package lifemonitor.application.model.jsonMapping;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

import lifemonitor.application.model.medicalRecord.Shape;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class ShapeDeserializer extends JsonDeserializer<Shape> {

        public ShapeDeserializer() {
            super();
        }

        @Override
        public Shape deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String shape = jp.readValueAs(String.class);

            return Shape.valueOf(shape.toUpperCase());
        }
}
