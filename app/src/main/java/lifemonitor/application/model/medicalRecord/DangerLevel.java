package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import lifemonitor.application.R;


/**
 * Created by leaf on 05/11/14.
 */
public enum DangerLevel {
    LEVEL1(R.string.danger_level1_description),
    LEVEL2(R.string.danger_level2_description),
    LEVEL3 (R.string.danger_level3_description);

    private int  resId;

    private DangerLevel(int  resId) {
        this.resId = resId;
    }


    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}
