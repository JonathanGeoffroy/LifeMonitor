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
    DERMAL(R.string.dermal),
    INTRAVENOUS(R.string.intravenous),
    AURICULAR(R.string.auricular),
    INTRAMUSCULAR(R.string.intramuscular),
    INHALED(R.string.inhaled),
    SUBLINGUAL(R.string.sublingual),
    OPHTALMIC(R.string.ophtalmic),
    PERINEURAL(R.string.perineural),
    NASAL(R.string.nasal),
    ENDOTRACHEOPULMONARY(R.string.endotracheopulmonary);

    private int  resId;

    private HowToTake(int resId) {
        this.resId = resId;
    }

    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}