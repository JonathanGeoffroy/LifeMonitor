package lifemonitor.application.model.medicalRecord;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.widget.medicalRecordItem.TreatmentInformationDialog;

/**
 * A Treatment must contain the following information: <br/>
 * - the date of beginning of treatment,<br/>
 * - the frequency, which is how many times you must follow the treatment,<br/>
 * - the quantity of medicine you must take,<br/>
 * - the medicine you take,<br/>
 * - the prescription which has determined the treatment (optional).
 *
 * @author Celia Cacciatore and Jonathan Geoffroy
 */
public class Treatment implements MedicalRecordItem {

    private final static long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

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
    private int frequency;

    /*
     * the quantity of medicine you must take
     */
    private double quantity;

    /*
     * the medicine to take
     */
    private Medicine medicine;

    /*
     * the prescription which determined the treatment (optional)
     */
    private Prescription prescription;

    /*
     * the number of day that the treatment lasts
     */
    private int duration;

    public Treatment() {}

    /**
     * Create a treatment.
     * @param date date of beginning of treatment
     * @param frequency how many times you must follow the treatment
     * @param quantity the quantity of medicine you must take
     * @param duration the number of days that the treatment lasts
     * @param medicine the medicine to take
     * @param prescription the prescription which determined the treatment
     */
    public Treatment(Date date, int frequency, double quantity, int duration, Medicine medicine, Prescription prescription) {
        this.date = date;
        this.frequency = frequency;
        this.quantity = quantity;
        this.duration = duration;
        this.medicine = medicine;
        this.prescription = prescription;
    }

    /**
     * Create a treatment.
     * @param date date of beginning of treatment
     * @param frequency how many times you must follow the treatment
     * @param quantity the quantity of medicine you must take
     * @param duration the number of days that the treatment lasts
     * @param medicine the medicine to take
     */
    public Treatment(Date date, int frequency, double quantity, int duration, Medicine medicine) {
        this.date = date;
        this.frequency = frequency;
        this.quantity = quantity;
        this.duration = duration;
        this.medicine = medicine;
    }

    /**
     * Compute the end date by adding <code>duration</code> to <code>date</code>
     * @return the end date of this Treatment
     */
    public Date getEndDate() {
        return new Date(date.getTime() + duration * MILLISECONDS_PER_DAY);
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Treatment treatment = (Treatment) o;

        if (duration != treatment.duration) return false;
        if (frequency != treatment.frequency) return false;
        if (id != treatment.id) return false;
        if (Double.compare(treatment.quantity, quantity) != 0) return false;
        if (date != null ? !date.equals(treatment.date) : treatment.date != null) return false;
        if (medicine != null ? !medicine.equals(treatment.medicine) : treatment.medicine != null)
            return false;
        if (prescription != null ? !prescription.equals(treatment.prescription) : treatment.prescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + frequency;
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (medicine != null ? medicine.hashCode() : 0);
        result = 31 * result + (prescription != null ? prescription.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.treatment_prepend_title) + medicine;
    }

    @Override
    public String getSubTitle(Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/y");
        return context.getString(R.string.treatment_prepend_subtitle) + simpleDateFormat.format(date);
    }

    @Override
    public int getColor() {
        return Color.argb(255, 223, 240, 216);
    }

    @Override
    public DialogFragment getInformation() {
        return new TreatmentInformationDialog();
    }
}
