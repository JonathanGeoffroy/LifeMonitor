package lifemonitor.application.helper.rest.parsers;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Specific parser for SingleResult request
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class SingleResultRESTParser<T> {

    /**
     * Parse the result
     * @param json the string version of json result to parse
     * @param clazz the class of the result
     * @throws java.io.IOException if error occurred when the result is parsed
     * @return T object contained in json
     */
    public T parseResult(String json, Class<T> clazz) throws IOException {
        // Jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));

        // Parses the result to get
        return mapper.readValue(json, clazz);
    }
}
