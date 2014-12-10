package lifemonitor.application.model.medicalRecord;

import java.util.Date;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Prescription {

    private int id;
    private Date date;
    private Doctor doctor;

    public Prescription() {}

    public Prescription(int id, Date date, Doctor doctor) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
