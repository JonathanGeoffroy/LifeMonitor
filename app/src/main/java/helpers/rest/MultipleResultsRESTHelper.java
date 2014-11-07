package helpers.rest;

import android.content.Context;
import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helpers.rest.listeners.MultipleResultsRESTListener;

/**
 * Specific RESTHelper for multiple results
 * Returns a list of <T> if REST request succeeded
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class MultipleResultsRESTHelper<T> extends RESTHelper<T> {
    protected MultipleResultsRESTListener<T> listener;

    public MultipleResultsRESTHelper(Context context, MultipleResultsRESTListener<T> listener) {
        super(context, listener);
        this.listener = listener;
    }

    /**
     * Parses the result to get a list of objects.
     * @param result json result.
     * @param clazz class of objects to get.
     * @return list of clazz objects.
     */
    @Override
    protected void parseResult(String result, Class<T> clazz) {
        List<T> listResults = new ArrayList<T>();

        // Jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Parses the result to get
            listResults = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            Log.e("RESTHelper", e.toString() + " " + e.getMessage());
        }

        listener.onGetResponse(listResults);
    }
}
