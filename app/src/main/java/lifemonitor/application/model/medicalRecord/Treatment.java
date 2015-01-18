package lifemonitor.application.model.medicalRecord;

import android.graphics.Color;

import java.text.SimpleDateFormat;
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
public class Treatment implements MedicalRecordItem {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Treatment treatment = (Treatment) o;

        if (id != treatment.id) return false;
        if (units != treatment.units) return false;
        if (!date.toString().equals(treatment.date.toString())) return false;
        if (!frequency.equals(treatment.frequency)) return false;
        if (!medicine.equals(treatment.medicine)) return false;
        if (prescription != null ? !prescription.equals(treatment.prescription) : treatment.prescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + date.hashCode();
        result = 31 * result + frequency.hashCode();
        result = 31 * result + units;
        result = 31 * result + medicine.hashCode();
        result = 31 * result + (prescription != null ? prescription.hashCode() : 0);
        return result;
    }

    @Override
    public String getTitle() {
        return "Traitement au " + medicine;
    }

    @Override
    public String getSubTitle() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/y");
        return "Depuis le " + simpleDateFormat.format(date);
    }

    @Override
    public int getColor() {
        return Color.argb(20, 78, 205, 196);
    }
}
