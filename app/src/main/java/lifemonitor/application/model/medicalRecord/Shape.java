package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;

import lifemonitor.application.R;
import lifemonitor.application.model.jsonMapping.ShapeDeserializer;

/**
 * Created by leaf on 22/10/14.
 */
@JsonDeserialize(using = ShapeDeserializer.class)
public enum Shape {
    GEL(R.string.gel),
    CAPSULE(R.string.capsule),
    PILLS(R.string.pills),
    OINTMENT(R.string.ointment),
    POWDER(R.string.powder),
    GAS(R.string.gas),
    SOLUTION(R.string.solution),
    PELLET(R.string.pellet),
    CREAM(R.string.cream),
    LIQUID(R.string.liquid),
    SUSPENSION(R.string.suspension),
    PLANT(R.string.Plant);

    private int resId;

    private Shape(int resId) {
        this.resId = resId;
    }

    public String resource(Context ctx) {
        return ctx.getString(resId);
    }
}