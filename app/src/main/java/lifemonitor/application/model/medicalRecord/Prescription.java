package lifemonitor.application.model.medicalRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Prescription implements Serializable {

    private static final long serialVersionUID = 8303367019659427160L;
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
