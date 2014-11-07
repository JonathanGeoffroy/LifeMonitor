package helpers.rest;

import android.content.Context;
import android.util.Log;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

import helpers.rest.listeners.SingleResultRESTListener;

/**
 * Specific RESTHelper for multiple results
 * Returns a <T> object if REST request succeeded, call onError otherwise
 *
 * @author Jonathan Geoffroy
 */
public class SingleResultRESTHelper<T> extends RESTHelper<T> {

    private final SingleResultRESTListener listener;

    public SingleResultRESTHelper(Context context, SingleResultRESTListener listener) {
        super(context, listener);
        this.listener = listener;
    }

    @Override
    protected void parseResult(String result, Class<T> clazz) throws IOException {
        T objectResult;
        // Jackson mapper
        ObjectMapper mapper = new ObjectMapper();

        // Parses the result to get
        objectResult = mapper.readValue(result, clazz);
        listener.onGetResponse(objectResult);
    }
}
