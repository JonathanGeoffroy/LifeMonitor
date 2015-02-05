package lifemonitor.application.model.medicalRecord;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.items.TodayTreatmentItem;
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
public class Treatment implements MedicalRecordItem, Serializable {

    private static final int MILLISECONDS_PER_MINUTE = 1000 * 60;
    private static final int MILLISECONDS_PER_HOUR = 1000 * 60 * 60;
    private final static long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    private static final long serialVersionUID = -1553669749402707344L;

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
    public Date computeEndDate() {
        return new Date(date.getTime() + duration * MILLISECONDS_PER_DAY);
    }

    /**
     * Compute every doses to take today for this Treatment. and add them to the provided <code>items</code>
     * The time of the first dose to take today is computed by using the <code>date</code> of the treatment.
     * So a time to take dose is the time of the <code>date</code>. Other doses to take appears in the day
     * in order to verify the<code>frequency</code>
     * example: if the treatement begins at 12:00, and frequency = 3, so added doses are:
     *  <ul>
     *      <li>4:00</li>
     *      <li>12:00</li>
     *      <li>20:00</li>
     *  </ul>
     *
     * @param items a list where to add computed doses
     */
    public void findTodayDoses(List<TodayTreatmentItem> items) {
        // Transform date to Calendar
        Calendar treatmentDate = new GregorianCalendar();
        treatmentDate.setTime(date);

        // Today at midnight
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        Date today = todayCalendar.getTime();

        // Tomorrow at midnight
        Calendar tomorrowCalendar = Calendar.getInstance();
        tomorrowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowCalendar.set(Calendar.MINUTE, 0);
        tomorrowCalendar.set(Calendar.SECOND, 0);
        tomorrowCalendar.set(Calendar.MILLISECOND, 0);
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = tomorrowCalendar.getTime();

        // If treatments have to be taken today
        Date endDate = new Date(date.getTime() + duration * MILLISECONDS_PER_DAY);
        if(date.before(tomorrow) && endDate.after(today)) {
            // Compute the date of the first dose to take today
            Calendar nextDoseCalendar = new GregorianCalendar();
            nextDoseCalendar.setTimeInMillis(date.getTime());

            long firstDoseMs = treatmentDate.get(Calendar.HOUR_OF_DAY) * MILLISECONDS_PER_HOUR + treatmentDate.get(Calendar.MINUTE) * MILLISECONDS_PER_MINUTE;
            firstDoseMs = firstDoseMs % (frequency * MILLISECONDS_PER_HOUR);
            int firstDoseHour = (int) (firstDoseMs / MILLISECONDS_PER_HOUR);
            int firstDoseMinute = (int) ((firstDoseMs - (firstDoseHour * MILLISECONDS_PER_HOUR)) / MILLISECONDS_PER_MINUTE);
            nextDoseCalendar.set(Calendar.HOUR_OF_DAY, firstDoseHour);
            nextDoseCalendar.set(Calendar.MINUTE, firstDoseMinute);
            nextDoseCalendar.set(Calendar.SECOND, 0);
            Date nextDose = nextDoseCalendar.getTime();

            // Compute all doses to take today
            while (nextDose.before(tomorrow)) {
                if(nextDose.after(today)) {
                    items.add(new TodayTreatmentItem(this, nextDose));
                }
                nextDose = new Date(nextDose.getTime() + frequency * MILLISECONDS_PER_HOUR);
            }
        }
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

    public void setQuantity(double quantity) {
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

        SimpleDateFormat format = new SimpleDateFormat("ymd");
        if (duration != treatment.duration) return false;
        if (frequency != treatment.frequency) return false;
        if (id != treatment.id) return false;
        if (Double.compare(treatment.quantity, quantity) != 0) return false;
        if (!format.format(date).equals(format.format(treatment.date))) return false;
        if (!medicine.equals(treatment.medicine)) return false;
        if (prescription != null ? !prescription.equals(treatment.prescription) : treatment.prescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + date.hashCode();
        result = 31 * result + frequency;
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + medicine.hashCode();
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
    public DialogFragment acceptInformation() {
        return new TreatmentInformationDialog();
    }
}
