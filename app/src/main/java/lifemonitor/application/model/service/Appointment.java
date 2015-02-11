package lifemonitor.application.model.service;

import java.util.Calendar;
import lifemonitor.application.model.medicalRecord.Patient;
import lifemonitor.application.model.medicalRecord.Doctor;

/**
 * Symbolize an appointment extracted from the database
 *
 * @author Romain Philippon
 */
public class Appointment {
    /**
     * Is the appointment id
     */
    private int id;
    /**
     * Is the doctor  with who user has the appointment
     */
    private Doctor doctor;
    /**
     * Is the appointment's date formatted according to this following format YYYY-MM-DDTHH:MM
     */
    private Calendar date;
    /**
     * Is the date format used to convert date to string and vice versa
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public Appointment() { }

    public Appointment(Doctor doctor, Calendar date) {
        super();
        this.setDoctor(doctor);
        this.setDate(date);
    }

    public int getId() {
        return this.id;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDoctor(Doctor newDoctor) {
        this.doctor = newDoctor;
    }

    public void setDate(Calendar newDate) {
        this.date = newDate;
    }
}
