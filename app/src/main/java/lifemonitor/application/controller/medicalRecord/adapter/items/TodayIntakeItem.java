package lifemonitor.application.controller.medicalRecord.adapter.items;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import lifemonitor.application.model.medicalRecord.Intake;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * Model for a Treatment to take Today.
 * Contains the treatment associated to the medicine to take, and the date of the dosage.
 * A specific <code>DateFormat</code> format this date in order to write it correctly
 *
 * @author Grégory Lefer, Jonathan Geoffroy
 */
public class TodayIntakeItem implements Comparable<TodayIntakeItem> {
    /**
     * Treatment to take
     */
    private Treatment treatment;

    private Intake intake;

    /**
     * DateFormat which displays hours:minutes
     */
    private final static DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public TodayIntakeItem(Intake intake, Treatment treatment) {
        this.intake = intake;
        this.treatment = treatment;
    }

    public String getMedicineName(Context context) {
        return treatment.getTitle(context);
    }

    public String getDate() {
        return dateFormat.format(intake.getTime());
    }

    public boolean isTaken() {
        return intake.getId() != 0;
    }

    public boolean isForgotten() {
        return !isTaken() && intake.isExpired();
    }

    public Intake getIntake() {
        return intake;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    @Override
    public int compareTo(TodayIntakeItem another) {
        return (int) (intake.getTime().getTime() - another.intake.getTime().getTime());
    }
}
