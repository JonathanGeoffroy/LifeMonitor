package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import lifemonitor.application.R;

/**
 * Created by leaf on 22/10/14.
 */
public enum Shape {
    PILLS(R.string.pills),
    OINTMENT(R.string.ointment),
    CAPSULE(R.string.capsule),
    POWDER(R.string.powder),
    PELLET(R.string.pellet),
    LIQUID(R.string.liquid),
    CREME(R.string.cream);

    private int  resId;

    private Shape(int  resId) {
        this.resId = resId;
    }


    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}