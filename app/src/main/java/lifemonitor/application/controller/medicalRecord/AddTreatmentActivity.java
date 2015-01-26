package lifemonitor.application.controller.medicalRecord;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.Spinner;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import lifemonitor.application.R;
import lifemonitor.application.controller.exceptions.medicalRecord.IllegalValueException;
import lifemonitor.application.controller.medicalRecord.adapter.MedicineOptionsAdapter;
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
public class AddTreatmentActivity extends FragmentActivity {

    private static final String DATEPICKER_TAG = "datepicker";
    private final static String[] quantityValues = {"0.5","1","1.5","2","2.5","3","3.5","4","4.5","5"};
    private final static long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);

        // Load startDate and endDate
        loadInstanceState(savedInstanceState);

        // Create datePickers for startDate and endDate
        createDatePicker(R.id.start_date, R.string.start_date_prefix, startDate);
        createDatePicker(R.id.end_date, R.string.end_date_prefix, endDate);

        // Change values in quantity NumberPicker
        NumberPicker quantityPicker = (NumberPicker) findViewById(R.id.quantity_picker);
        quantityPicker.setDisplayedValues(quantityValues);
        quantityPicker.setMaxValue(quantityValues.length - 1);
        quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                quantity = newVal;
            }
        });

        // Change values in frequency NumberPicker
        NumberPicker frequencyPicker = (NumberPicker) findViewById(R.id.frequency_number);
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
        Spinner spinner = (Spinner) findViewById(R.id.frequency_possibilities);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.frequency, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Load options for auto-complete medicine TextView
        final MedicineOptionsAdapter medicineOptionsAdapter = new MedicineOptionsAdapter(this, android.R.layout.simple_list_item_1);
        final AutoCompleteTextView medicineTextView = (AutoCompleteTextView) findViewById(R.id.medicine);
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
        Button submitTreatment = (Button) findViewById(R.id.submit_treatment);
        submitTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addTreatment();
                } catch (IllegalValueException e) {
                    Toast.makeText(AddTreatmentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Add a treatment by creating a treatment with GUI values and sending it to REST Service.
     * Check the GUI values, and throw an <code>IllegalValueException</code> with specific message for each error
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
        Treatment treatment = new Treatment(
                startDate.getTime(),
                frequency,
                quantity,
                duration,
                medicine,
                null);
        // Add the new Treatment into REST Service
        RESTHelper<Treatment> restHelper = new RESTHelper<>(this);
        restHelper.sendPOSTRequest(treatment, "/files/" + PATIENT_ID + "/treatments", Treatment.class, new PostListener<Treatment>() {
            @Override
            public void onSuccess(Treatment addedObject) {
                AddTreatmentActivity.this.finish();
            }
            @Override
            public void onError() {
                Toast.makeText(AddTreatmentActivity.this, R.string.addTreatment_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Create a new DatePickerDialog which updates the calendar and the text button as soon as the date changes
     * @param buttonId the button id to change
     * @param prefixStringId the prefix of the text button
     * @param calendar the calendar to update
     * @return the created calendar
     */
    private DatePickerDialog createDatePicker(int buttonId, final int prefixStringId, final Calendar calendar) {
        final int currentYear = calendar.get(Calendar.YEAR);
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final Button button = (Button) findViewById(buttonId);
        // When user clicked on button, display the DatePickerDialog
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(currentYear, currentYear + 10);
                datePickerDialog.setCloseOnSingleTapDay(true);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
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
    protected void onSaveInstanceState(Bundle outState) {
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
        NumberPicker frequencyNumberPicker = (NumberPicker) findViewById(R.id.frequency_number);
        int frequencyNumber = frequencyNumberPicker.getValue();
        // Get selected frequency between possibilities
        Spinner frequencySpinner = (Spinner) findViewById(R.id.frequency_possibilities);
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
        NumberPicker quantityPicker = (NumberPicker) findViewById(R.id.quantity_picker);
        return Double.parseDouble(quantityValues[quantityPicker.getValue()]);
    }

    /**
     * Compute duration from GUI
     * @return number of days of treatment
     */
    private int getDuration() {
        return (int) ((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / MILLISECONDS_PER_DAY) + 1;
    }
}