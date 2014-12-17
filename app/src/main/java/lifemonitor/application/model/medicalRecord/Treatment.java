package lifemonitor.application.model.medicalRecord;

import java.util.Date;

/**
 * A Treatment must contain the following information: <br/>
 * - the date of beginning of treatment,<br/>
 * - the frequency, which is how many times you must follow the treatment,<br/>
 * - the quantity of medicine you must take (units),<br/>
 * - the medicine you take,<br/>
 * - the prescription which has determined the treatment (optional).
 *
 * @author Celia Cacciatore and Jonathan Geoffroy
 */
public class Treatment {

    /*
     * The id is automatically generated in database.
     */
    private int id;

    /*
     * date of beginning of treatment
     */
    private Date date;

    /*
     * how many times you must follow the treatment
     */
    private String frequency;

    /*
     * the quantity of medicine you must take
     */
    private int units;

    /*
     * the medicine to take
     */
    private Medicine medicine;

    /*
     * the prescription which determined the treatment (optional)
     */
    private Prescription prescription;

    public Treatment() {}

    /**
     * Create a treatment.
     * @param date date of beginning of treatment
     * @param frequency how many times you must follow the treatment
     * @param units the quantity of medicine you must take
     * @param medicine the medicine to take
     * @param prescription the prescription which determined the treatment
     */
    public Treatment(Date date, String frequency, int units, Medicine medicine, Prescription prescription) {
        this.date = date;
        this.frequency = frequency;
        this.units = units;
        this.medicine = medicine;
        this.prescription = prescription;
    }

    /**
     * Create a treatment.
     * @param date date of beginning of treatment
     * @param frequency how many times you must follow the treatment
     * @param units the quantity of medicine you must take
     * @param medicine the medicine to take
     */
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
