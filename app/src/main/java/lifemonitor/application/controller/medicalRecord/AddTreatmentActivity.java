package lifemonitor.application.controller.medicalRecord;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.Spinner;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import lifemonitor.application.R;
import lifemonitor.application.controller.exceptions.medicalRecord.IllegalValueException;
import lifemonitor.application.controller.medicalRecord.adapter.MedicineOptionsAdapter;
import lifemonitor.application.controller.monitor.TreatmentBroadcastReceiver;
import lifemonitor.application.controller.monitor.TreatmentNotifier;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.medicalRecord.Frequency;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * Add a new Treatment manually.<br/>
 * Show a form in order to enter the values of the new Treatment.<br/>
 * Add the treatment to DB by calling the REST Service helper.<br/>
 * Check values entered by user. Show a specific message if the treatment is not conform as expected,
 * and just quit the Activity in case of success.
 */
public class AddTreatmentActivity extends Fragment {

    private static final String DATEPICKER_TAG = "datepicker";
    private final static String[] quantityValues = {"0.5","1","1.5","2","2.5","3","3.5","4","4.5","5"};
    private final static long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
    private final static long MILLISECONDS_PER_HOUR = 60 * 60 * 1000;

    /**
     * Bundle key for each value
     */
    private static final String
    START_DATE_BUNDLE_KEY = "startDate",
    MEDICINE_BUNDLE_KEY = "medicine",
    END_DATE_BUNDLE_KEY = "endDate",
    FREQUENCY_BUNDLE_KEY = "frequency",
    QUANTITY_BUNDLE_KEY = "quantity";

    // TODO: get real patient id from authentication
    private static final String PATIENT_ID = "1";

    /**
     * Minimal and maximal values for frequency
     */
    private static final int FREQUENCY_MIN_VALUE = 1;
    private static final int FREQUENCY_MAX_VALUE = 12;

    /**
     * The treatment start and end dates, chosen by user
     */
    private Calendar startDate;
    private Calendar endDate;

    /**
     * Fields for Treatment
     */
    private Medicine medicine;
    private double quantity;
    private int frequency = 1;
    private Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_add_treatment, container, false);

        // Load startDate and endDate
        loadInstanceState(savedInstanceState);

        // Create datePickers for startDate and endDate
        createDatePicker(rootView,R.id.start_date, R.string.start_date_prefix, startDate);
        createDatePicker(rootView,R.id.end_date, R.string.end_date_prefix, endDate);

        // Change values in quantity NumberPicker
        NumberPicker quantityPicker = (NumberPicker) rootView.findViewById(R.id.quantity_picker);
        quantityPicker.setDisplayedValues(quantityValues);
        quantityPicker.setMaxValue(quantityValues.length - 1);
        quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                quantity = newVal;
            }
        });

        // Change values in frequency NumberPicker
        NumberPicker frequencyPicker = (NumberPicker) rootView.findViewById(R.id.frequency_number);
        frequencyPicker.setMinValue(FREQUENCY_MIN_VALUE);
        frequencyPicker.setMaxValue(FREQUENCY_MAX_VALUE);
        frequencyPicker.setValue(frequency);
        frequencyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                frequency = newVal;
            }
        });

        // Change frequency Spinner values
        Spinner spinner = (Spinner) rootView.findViewById(R.id.frequency_possibilities);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(rootView.getContext(),R.array.frequency, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Load options for auto-complete medicine TextView
        final MedicineOptionsAdapter medicineOptionsAdapter = new MedicineOptionsAdapter(rootView.getContext(), android.R.layout.simple_list_item_1);
        final AutoCompleteTextView medicineTextView = (AutoCompleteTextView) rootView.findViewById(R.id.medicine);
        medicineTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                if (value.length() >= 3) {
                    medicineOptionsAdapter.onDataChanged(value);
                }
            }
        });
        medicineTextView.setAdapter(medicineOptionsAdapter);

        // If user chooses a medicine, put it in class field
        medicineTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicine = medicineOptionsAdapter.getMedicine(position);
            }
        });

        // Add the treatment in database or show error toast
        Button submitTreatment = (Button) rootView.findViewById(R.id.submit_treatment);
        submitTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addTreatment();
                } catch (IllegalValueException e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    /**
     * Add a treatment by creating a treatment with GUI values and sending it to REST Service.
     * Check the GUI values, and throw an <code>IllegalValueException</code> with specific message for each error.
     * Trigger notifications if checked.
     * @throws IllegalValueException if values aren't conform to expected.
     */
    private void addTreatment() throws IllegalValueException {
        // Check dates
        // Start date can't be before today
        // End date can't be before start date
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        if (startDate.before(yesterday)) {
            throw new IllegalValueException(getString(R.string.startDateBeforeTodayError));
        }
        if (endDate.before(startDate)) {
            throw new IllegalValueException(getString(R.string.startDateAfterEndDate));
        }
        // Check medicine : must be chosen from list
        if (medicine == null) {
            throw new IllegalValueException(getString(R.string.medicineNotChosen));
        }
        // Check quantity : must be a positive number
        double quantity;
        try {
            quantity = getQuantity();
            if(quantity <= 0) {
                throw new IllegalValueException(getString(R.string.notChosenUnits));
            }
        } catch (NumberFormatException e) {
            throw new IllegalValueException(getString(R.string.notChosenUnits));
        }
        // Get frequency
        int frequency = getFrequency();
        // Get duration
        int duration = getDuration();
        // Create a Treatment with GUI values and no prescription
        final Treatment treatment = new Treatment(
                startDate.getTime(),
                frequency,
                quantity,
                duration,
                medicine,
                null);
        // Add the new Treatment into REST Service
        RESTHelper<Treatment> restHelper = new RESTHelper<>(getActivity().getApplicationContext());
        restHelper.sendPOSTRequest(treatment, "/files/" + PATIENT_ID + "/treatments", Treatment.class, new PostListener<Treatment>() {
            @Override
            public void onSuccess(final Treatment addedObject) {
                // Check if user wants to be notified
                CheckBox notificationCheckbox = (CheckBox) getActivity().findViewById(R.id.notification_checkBox);
                if(notificationCheckbox.isChecked()) {
                    triggerNotifications(addedObject);
                }

                // Exit
                getFragmentManager().popBackStackImmediate();
            }
            @Override
            public void onError() {
                Toast.makeText(getActivity().getApplicationContext(), R.string.addTreatment_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Create a new DatePickerDialog which updates the calendar and the text button as soon as the date changes
     *
     * @param rootView
     * @param buttonId the button id to change
     * @param prefixStringId the prefix of the text button
     * @param calendar the calendar to update
     * @return the created calendar
     */
    private DatePickerDialog createDatePicker(View rootView, int buttonId, final int prefixStringId, final Calendar calendar) {
        final int currentYear = calendar.get(Calendar.YEAR);
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final Button button = (Button) rootView.findViewById(buttonId);
        // When user clicked on button, display the DatePickerDialog
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(currentYear, currentYear + 10);
                datePickerDialog.setCloseOnSingleTapDay(true);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        // On date changed, update the text button to chosen date
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                calendar.set(year, month, day);
                onDateChanged(button, prefixStringId, calendar);
            }
        });
        // Initialize the text button to current date
        onDateChanged(button, prefixStringId, calendar);
        return datePickerDialog;
    }

    /**
     * Change the button text by displaying the selected calendar date
     * @param button the button to change text
     * @param prefixStringId the prefix of button text
     * @param calendar the date to display
     */
    private void onDateChanged(Button button, int prefixStringId, Calendar calendar) {
        StringBuilder date = new StringBuilder(getResources().getText(prefixStringId))
                .append(" ")
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append("/")
                .append(calendar.get(Calendar.MONTH) + 1)
                .append("/")
                .append(calendar.get(Calendar.YEAR));
        button.setText(date);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(START_DATE_BUNDLE_KEY, startDate);
        outState.putSerializable(END_DATE_BUNDLE_KEY, endDate);
        outState.putSerializable(MEDICINE_BUNDLE_KEY, medicine);
        outState.putSerializable(FREQUENCY_BUNDLE_KEY, frequency);
        outState.putSerializable(QUANTITY_BUNDLE_KEY, quantity);
        super.onSaveInstanceState(outState);
    }

    /**
     * Reload chosen date for <code>startDate</code> and <code>endDate</code> calendars.<br/>
     * These calendars should be saved by auto-calling the <code>onSaveInstanceState</code> method.
     * If there is no saved bundle, initialize <code>startDate</code> and <code>endDate</code> with default calendar,
     * which displays the today's date.
     * @param savedInstanceState the Bundle previously saved by auto-calling the <code>onSaveInstanceState</code> method.
     */
    private void loadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            startDate = Calendar.getInstance();
            endDate = Calendar.getInstance();
        } else {
            startDate = (Calendar) savedInstanceState.getSerializable(START_DATE_BUNDLE_KEY);
            medicine = (Medicine) savedInstanceState.getSerializable(MEDICINE_BUNDLE_KEY);
            endDate = (Calendar) savedInstanceState.getSerializable(END_DATE_BUNDLE_KEY);
            frequency = savedInstanceState.getInt(FREQUENCY_BUNDLE_KEY, 1);
            quantity = savedInstanceState.getDouble(QUANTITY_BUNDLE_KEY, 0.5);
        }
    }

    /**
     * Compute frequency from GUI
     * @return the number of hours between two doses
     */
    public int getFrequency() {
        // Get number of times
        NumberPicker frequencyNumberPicker = (NumberPicker) getActivity().findViewById(R.id.frequency_number);
        int frequencyNumber = frequencyNumberPicker.getValue();
        // Get selected frequency between possibilities
        Spinner frequencySpinner = (Spinner) getActivity().findViewById(R.id.frequency_possibilities);
        int frequencyIndex = frequencySpinner.getSelectedItemPosition();
        Frequency selectedFrequency = Frequency.values()[frequencyIndex];
        // Compute frequency
        int frequency = selectedFrequency.getHours() / frequencyNumber;
        if (frequency <= 0) {
            frequency = 1;
        }
        return frequency;
    }

    /**
     * Compute quantity from GUI
     * @return the quantity of medicine to take
     */
    public double getQuantity() {
        NumberPicker quantityPicker = (NumberPicker) getActivity().findViewById(R.id.quantity_picker);
        return Double.parseDouble(quantityValues[quantityPicker.getValue()]);
    }

    /**
     * Compute duration from GUI
     * @return number of days of treatment
     */
    private int getDuration() {
        return (int) ((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / MILLISECONDS_PER_DAY) + 1;
    }

    /**
     * Create two notifications :
     * one that appears 10 seconds after adding the treatment,
     * and one that is repeated for each taking of the medicine.
     * @param treatment the treatment recalled in notifications
     */
    private void triggerNotifications(final Treatment treatment) {
        // Create notification which is repeated
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), TreatmentBroadcastReceiver.class);
        intent.putExtra("treatment", treatment);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, 0);
        Date startDate = treatment.getDate();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startDate.getTime(), treatment.getFrequency() * MILLISECONDS_PER_HOUR, alarmIntent);

        // Make the notification appear 10 seconds after adding the treatment
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                TreatmentNotifier.createNotification(mActivity.getApplicationContext(), treatment);
            }
        };
        timer.schedule(timerTask, 10000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}