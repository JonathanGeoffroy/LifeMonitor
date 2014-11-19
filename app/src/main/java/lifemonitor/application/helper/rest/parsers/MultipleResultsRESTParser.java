package lifemonitor.application.helper.rest.parsers;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;

/**
 * Specific parser for MultipleResults request
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class MultipleResultsRESTParser<T> {
    /**
     * Parse the result
     * @param json the string version of json result to parse
     * @param clazz the class of the result
     * @throws java.io.IOException if error occurred when the result is parsed
     * @return list of T objects contained in json
     */
    public List<T> parseResult(String json, Class<T> clazz) throws IOException {
        // Jackson mapper
        ObjectMapper mapper = new ObjectMapper();

        // Parses the result to get
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
