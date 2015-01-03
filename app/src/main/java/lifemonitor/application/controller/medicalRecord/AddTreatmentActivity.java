package lifemonitor.application.controller.medicalRecord;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import lifemonitor.application.R;
import lifemonitor.application.controller.exceptions.medicalRecord.IllegalValueException;
import lifemonitor.application.controller.medicalRecord.adapter.MedicineOptionsAdapter;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Prescription;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * This activity is ran when user want add a new Treatment manually.<br/>
 * Show a form in order to enter the values of the new Treatment.
 * Add the treatment to DB by calling the REST Service helper.
 * Check values entered by user. Show a specific message if the treatment is not conform as expected,
 * and just quit the Activity in case of success.
 */
public class AddTreatmentActivity extends FragmentActivity {

    private static final String DATEPICKER_TAG = "datepicker";
    /**
     * bundle key for each value
     */
    private static final String
            START_DATE_BUNDLE_KEY = "startDate",
            END_DATE_BUNDLE_KEY = "endDate",
            DURATION_BUNDLE_KEY = "duration";
    private static final int DURATION_MIN_VALUE = 1;
    private static final int DURATION_MAX_VALUE = 365;

    /**
     * The treatment start date, chosen by user
     */
    private Calendar startDate;
    /**
     * The treatment end date, chosen by user
     */
    private Calendar endDate;

    /**
     * The treatment duration
     */
    private int duration;

    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);

        // Load startDate and endDate
        loadInstanceState(savedInstanceState);

        // Create datePickers for startDate and endDate
        createDatePicker(R.id.start_date, R.string.start_date_prefix, startDate);
        createDatePicker(R.id.end_date, R.string.end_date_prefix, endDate);

        // Change default NumberPicker values
        NumberPicker durationPicker = (NumberPicker) findViewById(R.id.duration);
        durationPicker.setMinValue(DURATION_MIN_VALUE);
        durationPicker.setMaxValue(DURATION_MAX_VALUE);
        durationPicker.setValue(duration);
        durationPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                duration = newVal;
            }
        });

        // Load options for auto-complete medicine TextView
        final MedicineOptionsAdapter adapter = new MedicineOptionsAdapter(this, android.R.layout.simple_list_item_1);
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
                    adapter.onDataChanged(value);
                }
            }
        });
        medicineTextView.setAdapter(adapter);

        // If user choose a medicine, put it in class field
        medicineTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicine = adapter.getMedicine(position);
            }
        });
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
     *
     * @throws IllegalValueException if values aren't conform to expected.
     */
    private void addTreatment() throws IllegalValueException {
        // Check GUI values
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        if (startDate.before(yesterday)) {
            throw new IllegalValueException(getString(R.string.startDateBeforeTodayError));
        }
        if (startDate.after(endDate)) {
            throw new IllegalValueException(getString(R.string.startDateAfterEndDate));
        }
        if (medicine == null) {
            throw new IllegalValueException(getString(R.string.medicineNotChosen));
        }

        // Create a Treatment with GUI values
        Treatment treatment = new Treatment(
                startDate.getTime(),
                getFrequency(),
                getUnits(),
                medicine,
                getPrescription());
        // Add the new Treatment into REST Service
        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(this);
        restHelper.sendPOSTRequest(treatment, "/treatments/", Treatment.class, new PostListener<Treatment>() {
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
     *
     * @param buttonId       the button id to change
     * @param prefixStringId the prefix of the text button
     * @param calendar       the calendar to update
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
     *
     * @param button         the button to change text
     * @param prefixStringId the prefix of button text
     * @param calendar       the date to display
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
        outState.putInt(DURATION_BUNDLE_KEY, duration);
        super.onSaveInstanceState(outState);
    }

    /**
     * Reload  chosen date for <code>startDate</code> and <code>endDate</code> calendars<br/>
     * These calendars should be saved by auto-calling the <code>onSaveInstanceState</code> method.
     * If there is no saved bundle, initialize <code>startDate</code> and <code>endDate</code> with default calendar,
     * which displays the today's date
     *
     * @param savedInstanceState the Bundle previously saved by auto-calling the <code>onSaveInstanceState</code> method.
     */
    private void loadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            startDate = Calendar.getInstance();
            endDate = Calendar.getInstance();
            duration = DURATION_MIN_VALUE;
        } else {
            startDate = (Calendar) savedInstanceState.getSerializable(START_DATE_BUNDLE_KEY);
            endDate = (Calendar) savedInstanceState.getSerializable(END_DATE_BUNDLE_KEY);
            duration = savedInstanceState.getInt(DURATION_BUNDLE_KEY);
        }
    }

    public String getFrequency() {
        EditText frequencyEditText = (EditText) findViewById(R.id.frequency);
        return frequencyEditText.getText().toString();
    }

    public int getUnits() {
        EditText unitsEditText = (EditText) findViewById(R.id.units);
        return Integer.parseInt(unitsEditText.getText().toString());
    }

    public Prescription getPrescription() {
        return null;
    }
}
