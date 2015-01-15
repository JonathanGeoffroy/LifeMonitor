package lifemonitor.application.helper.rest.parsers;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Transforms an Object into JSON.
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class ObjectToJSONParser {

    public String getJSONFrom(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
        ObjectWriter objectWriter = objectMapper.writer();
        return objectWriter.writeValueAsString(object);
    }
}
