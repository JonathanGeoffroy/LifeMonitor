package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import lifemonitor.application.R;
import lifemonitor.application.model.jsonMapping.HowToTakeDeserializer;

/**
 * Created by leaf on 22/10/14.
 */
@JsonDeserialize(using = HowToTakeDeserializer.class)
public enum HowToTake {
    ORAL(R.string.oral),
    DILUTED(R.string.diluted),
    SUBLINGUAL(R.string.sublingual),
    INJECTION(R.string.injection),
    LOCAL_APPLICATION(R.string.dermal_application);

    private int  resId;

    private HowToTake(int resId) {
        this.resId = resId;
    }

    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}