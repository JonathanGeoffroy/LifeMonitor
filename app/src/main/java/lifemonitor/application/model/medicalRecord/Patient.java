package lifemonitor.application.model.medicalRecord;

import java.io.Serializable;

import lifemonitor.application.model.medicalRecord.MedicalRecord;

/**
 * Represents a json patient object from restful database
 *
 * @author Romain Philippon
 */
public class Patient implements Serializable {
    private static final long serialVersionUID = -3516580140054817293L;
    private int id;
    private String name;
    private MedicalRecord medical_file;

    public Patient() { }

    public Patient(int id, String name, MedicalRecord medical_file) {
        this.id = id;
        this.name = name;
        this.medical_file = medical_file;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public MedicalRecord getMedical_file() {
        return this.medical_file;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setMedical_file(MedicalRecord medical_file) {
        this.medical_file = medical_file;
    }
}
