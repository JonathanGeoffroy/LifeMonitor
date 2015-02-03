package lifemonitor.application.controller.medicalRecord.adapter.items;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * Model for a Treatment to take Today.
 * Contains the treatment associated to the medicine to take, and the date of the dosage.
 * A specific <code>DateFormat</code> format this date in order to write it correctly
 *
 * @author Grégory Lefer, Jonathan Geoffroy
 */
public class TodayTreatmentItem {
    /**
     * Treatment to take
     */
    private Treatment treatment;

    /**
     * Date of the dose
     */
    private Date dose;

    /**
     * Does this Dose already been taken ?
     */
    private boolean taken;

    /**
     * DateFormat which displays hours::minutes
     */
    private final static DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public TodayTreatmentItem(Treatment treatment, Date dose) {
        this.treatment = treatment;
        this.dose = dose;
    }

    public String getMedicineName(Context context) {
        return treatment.getTitle(context);
    }

    public String getDate() {
        return dateFormat.format(dose);
    }

    public boolean isTaken() {
        return taken;
    }
}
