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
    ORAL(R.string.Oral),
    DERMAL(R.string.Dermal),
    INTRAVENOUS(R.string.Intravenous),
    AURICULAR(R.string.Auricular),
    INTRAMUSCULAR(R.string.Intramuscular),
    INHALED(R.string.Inhaled),
    SUBLINGUAL(R.string.Sublingual),
    OPHTALMIC(R.string.Ophtalmic),
    PERINEURAL(R.string.Perineural),
    NASAL(R.string.Nasal),
    ENDOTRACHEOPULMONARY(R.string.Endotracheopulmonary);

    private int  resId;

    private HowToTake(int resId) {
        this.resId = resId;
    }

    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}