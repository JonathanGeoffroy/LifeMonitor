package lifemonitor.application.controller.service;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.service.adapter.DoctorAdapter;
import lifemonitor.application.database.LocalDataBase;
import lifemonitor.application.database.RemoteDataBase;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.User;
import lifemonitor.application.model.medicalRecord.Patient;
import lifemonitor.application.model.medicalRecord.Doctor;
import lifemonitor.application.model.service.Appointment;

/**
 * @author Romain Philippon
 */
public class AddMedicalAppointment extends Fragment {

    private TextView textDayAppointment;
    private int chosenHour, chosenMinute;
    private Calendar chosenDate;
    private Doctor chosenDoctor;
    private int MIN_HOUR = 0;
    private int MAX_HOUR = 23;
    private int MIN_MINUTE = 0;
    private int MAX_MINUTE = 59;
    private int MIN_PATTERN_LENGTH = 3;
    private DoctorAdapter adapter;
    private LinkedList<Doctor> doctors;
    private AutoCompleteTextView doctorInputText;

    private static String FLAG_LOG = "AddMedicalAppointment Class";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.chosenHour = this.chosenMinute = 0;
        this.chosenDate = Calendar.getInstance();
        this.doctors = new LinkedList<>();
        this.adapter = new DoctorAdapter(this.getActivity(), android.R.layout.simple_list_item_1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_add_appointment_doctor, null);

        /* UI : CHOOSE DOCTORS */
        this.initAdapter();
        this.doctorInputText = (AutoCompleteTextView) layout.findViewById(R.id.chooseDoctorAppointment);

        final int PATIENT_ID = 1;
        try {
            User user = new LocalDataBase(this.getActivity()).getUser(PATIENT_ID);
            this.doctorInputText.setText(user.getDrName());
        }
        catch (SQLException sqle) {  }


        doctorInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String pattern = s.toString();

                if (pattern.length() >= MIN_PATTERN_LENGTH) {
                    AddMedicalAppointment.this.reduceDoctorSearch(pattern);
                }
            }
        });

        doctorInputText.setAdapter(this.adapter);

        // If user chooses a medicine, put it in class field
        doctorInputText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddMedicalAppointment.this.chosenDoctor = AddMedicalAppointment.this.adapter.getDoctor(position);
            }
        });

        /* UI : DATE APPOINTMENT */
        this.textDayAppointment = (TextView)layout.findViewById(R.id.dateDayAppointmentDoctor);
        Button modifyDayAppointment = (Button)layout.findViewById(R.id.changeDateDayAppointmentDoctor);
        makeDatePickerDialog(modifyDayAppointment);

        /* UI : HOUR APPOINTMENT */
        NumberPicker pickerHourAppointment = (NumberPicker)layout.findViewById(R.id.hourAppointment);
        NumberPicker pickerMinuteAppointment = (NumberPicker)layout.findViewById(R.id.minuteAppointment);
        populateNumberPicker(MIN_HOUR, MAX_HOUR, pickerHourAppointment);
        populateNumberPicker(MIN_MINUTE, MAX_MINUTE, pickerMinuteAppointment);

        // Pickers events
        pickerHourAppointment.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                AddMedicalAppointment.this.chosenHour = newVal;
            }
        });

        pickerMinuteAppointment.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                AddMedicalAppointment.this.chosenMinute = newVal;
            }
        });

         /* UI : SUBMIT BUTTON */
        Button submitButton = (Button)layout.findViewById(R.id.appendAppointmentDoctor);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicalAppointment.this.appendAppointmentDoctor();
            }
        });

        return layout;
    }

    public DatePickerDialog makeDatePickerDialog(Button button) {
        final String TAG_DIALOG = "dialogChooseDateAppointmentDoctor";
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                null,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                false
        );

        // When user clicked on button, display the DatePickerDialog
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 10);
                datePickerDialog.setCloseOnSingleTapDay(true);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), TAG_DIALOG);
            }
        });

        // On date changed, update the text button to chosen date
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                AddMedicalAppointment.this.chosenDate.set(year, month, day);
                onDateChanged();
            }
        });

        return datePickerDialog;
    }

    public void onDateChanged() {
        String year = Integer.toString(this.chosenDate.get(Calendar.YEAR));
        String month = ((this.chosenDate.get(Calendar.MONTH) + 1) < 10) ? "0"+ (this.chosenDate.get(Calendar.MONTH) + 1) : Integer.toString(this.chosenDate.get(Calendar.MONTH) + 1);
        String day = (this.chosenDate.get(Calendar.DAY_OF_MONTH) < 10) ? "0"+ this.chosenDate.get(Calendar.DAY_OF_MONTH) : Integer.toString(this.chosenDate.get(Calendar.DAY_OF_MONTH));

        String displayingText = String.format("%s / %s / %s", day, month, year);

        this.textDayAppointment.setText(displayingText);
    }

    public void populateNumberPicker(int startValue, int endValue, NumberPicker picker) {
        picker.setMinValue(startValue);
        picker.setMaxValue(endValue);
        picker.setValue(startValue);
    }

    private void appendAppointmentDoctor() {
        final int PATIENT_ID = 1;
        final String requestToParse = "/files/%d/appointments";
        final String request = String.format(requestToParse, PATIENT_ID);

        Appointment appointment = new Appointment(this.chosenDoctor, this.getDate(this.chosenDate, this.chosenHour, this.chosenMinute));

        new RESTHelper<Appointment>(this.getActivity()).sendPOSTRequest(appointment, request, Appointment.class, new PostListener<Appointment>() {
            @Override
            public void onSuccess(Appointment addedObject) {
                getFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onError() {

            }
        });

    }

    private void initAdapter() {
        final String request = "/doctors/all";
        final RESTHelper<Doctor> restHelper = new RESTHelper<Doctor>(this.getActivity());

        restHelper.sendGETRequestForMultipleResults(request, Doctor.class, new MultipleResultsRESTListener<Doctor>() {
            @Override
            public void onGetResponse(List<Doctor> results) {
                AddMedicalAppointment.this.doctors.addAll(results);
            }

            @Override
            public void onError() {

            }
        });

        this.adapter.addAllDoctors(this.doctors);
    }

    private void reduceDoctorSearch(String patternName) {
        this.adapter.clear();

        for(Doctor doc : this.doctors) {
            if(doc.getName().contains(patternName)) {
                this.adapter.addDoctor(doc);
            }
        }

        this.doctorInputText.setAdapter(this.adapter);
    }

    private Calendar getDate(Calendar calendar, int hour, int minute) {
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                hour,
                minute,
                0
        );

        return calendar;
    }
}
