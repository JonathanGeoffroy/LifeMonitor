package lifemonitor.application.helper.rest.parsers;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Transforms an Object into JSON.
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class ObjectToJSONParser {

    public String getJSONFrom(Object object) throws IOException {
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return objectWriter.writeValueAsString(object);
    }
}
