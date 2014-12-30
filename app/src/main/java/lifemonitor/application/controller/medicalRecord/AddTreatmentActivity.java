package lifemonitor.application.controller.medicalRecord;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import lifemonitor.application.R;

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
}
