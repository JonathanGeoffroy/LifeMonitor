package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import lifemonitor.application.R;

/**
 * Created by leaf on 22/10/14.
 */
public enum HowToConsume {
    ORALE(R.string.oral),
    DILUEE(R.string.diluted),
    SUBLINGUAL(R.string.sublingual),
    INJECTION(R.string.injection),
    APPLICATION_LOCALE(R.string.dermal_application);


    private int  resId;

    private HowToConsume(int  resId) {
        this.resId = resId;
    }


    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}