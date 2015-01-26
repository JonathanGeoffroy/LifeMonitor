package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import lifemonitor.application.R;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public enum Frequency {
    DAY(R.string.day,24),
    WEEK(R.string.week,24*7),
    MONTH(R.string.month,24*30),
    YEAR(R.string.year,24*365);

    private int resId;
    private int hours;

    private Frequency(int resId, int hours) {
        this.resId = resId;
        this.hours = hours;
    }

    public String resource(Context ctx) {
        return ctx.getString(resId);
    }

    public int getResId() {
        return resId;
    }

    public int getHours() {
        return this.hours;
    }
}