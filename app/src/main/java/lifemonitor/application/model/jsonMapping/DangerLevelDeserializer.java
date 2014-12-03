package lifemonitor.application.model.jsonMapping;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

import lifemonitor.application.model.medicalRecord.DangerLevel;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class DangerLevelDeserializer extends JsonDeserializer<DangerLevel> {

    @Override
    public DangerLevel deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int dangerLevel = jp.getIntValue();
        return DangerLevel.valueOf("LEVEL" + dangerLevel);
    }
}
