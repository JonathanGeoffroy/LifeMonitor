package lifemonitor.application.model.medicalRecord;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Date;

/**
 * @author Celia Cacciatore and Jonathan Geoffroy
 */
public class Treatment {

    private int id;
    private Date date;
    private String frequency;
    private int units;
    private Medicine medicine;
    private Prescription prescription;

    public Treatment() {}

    @JsonCreator
    public Treatment(int id, Date date, String frequency, int units, Medicine medicine, Prescription prescription) {
        this.id = id;
        this.date = date;
        this.frequency = frequency;
        this.units = units;
        this.medicine = medicine;
        this.prescription = prescription;
    }

    public Treatment(Date date, String frequency, int units, Medicine medicine) {
        this.date = date;
        this.frequency = frequency;
        this.units = units;
        this.medicine = medicine;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String toString() {
        return "Treatment = " + id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
