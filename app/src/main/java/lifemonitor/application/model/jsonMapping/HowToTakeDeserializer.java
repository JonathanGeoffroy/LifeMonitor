package lifemonitor.application.model.jsonMapping;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

import lifemonitor.application.model.medicalRecord.HowToTake;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class HowToTakeDeserializer extends JsonDeserializer<HowToTake> {

    @Override
    public HowToTake deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String howToTake = jp.readValueAs(String.class);
        return HowToTake.valueOf(howToTake.toUpperCase());
    }
}
