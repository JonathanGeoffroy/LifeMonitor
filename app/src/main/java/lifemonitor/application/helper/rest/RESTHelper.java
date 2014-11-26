package lifemonitor.application.helper.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.List;

import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.helper.rest.listeners.RESTListener;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.helper.rest.parsers.MultipleResultsRESTParser;
import lifemonitor.application.helper.rest.parsers.SingleResultRESTParser;

/**
 * Sends HTTP requests to REST service, parses JSON result to get a list of T objects and gives this list to the Requester.
 *
 * @author Celia Cacciatore, Jonathan Geoffroy
 */
public class RESTHelper<T> {

    /**
     * REST service address.
     */
    private static String RESTUrl = "http://leferazure.cloudapp.net";
    public static String getRESTUrl() {
        return RESTUrl;
    }
    public static void setRESTUrl(String RESTUrl) {
        RESTHelper.RESTUrl = RESTUrl;
    }

    private RequestQueue queue;

    protected Context context;

    public RESTHelper(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    /**
     * Send a GET request for single result.<br>
     * Call <code>restListener.onGetResponse</code> if request succeeded, <code>restListener.onError</code> otherwise
     *
     * @param uri uri, begins with "/" (for example "/treatment"), will be concatenated with RESTUrl.
     * @param clazz class of objects to get.
     * @param restListener the listener which is called when the request ended.
     */
    public void sendGETRequestForSingleResult(String uri, final Class<T> clazz, final SingleResultRESTListener<T> restListener) {
        Response.Listener responseListener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                SingleResultRESTParser<T> parser = new SingleResultRESTParser();
                String result = (String) o;
                // Parses the result to get a list of objects
                try {
                    T parsedResult = parser.parseResult(result, clazz);
                    restListener.onGetResponse(parsedResult);
                } catch (IOException e) {
                    sendError(e, restListener);
                }
            }
        };
        sendGETRequest(uri, restListener, responseListener);
    }

    /**
     * Send a GET request for multiple results.<br>
     * Call <code>restListener.onGetResponse</code> if request succeeded, <code>restListener.onError</code> otherwise
     *
     * @param uri uri, begins with "/" (for example "/treatment"), will be concatenated with RESTUrl.
     * @param clazz class of objects to get.
     * @param restListener the listener which is called when the request ended.
     */
    public void sendGETRequestForMultipleResults(String uri, final Class<T> clazz, final MultipleResultsRESTListener<T> restListener) {
        // Request a string response from the provided URL.
        Response.Listener responseListener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                MultipleResultsRESTParser<T> parser = new MultipleResultsRESTParser();
                String result = (String) o;
                // Parses the result to get a list of objects
                try {
                    List<T> parsedResult = parser.parseResult(result, clazz);
                    restListener.onGetResponse(parsedResult);
                } catch (IOException e) {
                    sendError(e, restListener);
                }
            }
        };
        sendGETRequest(uri, restListener, responseListener);
    }

    /**
     * Launch a GET request by running responseListener when request succeeds, else provides a default error.
     * @param uri uri, begins with "/" (for example "/treatment"), will be concatenated with RESTUrl.
     * @param restListener listener called when the request is sent
     * @param responseListener run when GET request succeeds
     */
    private void sendGETRequest(String uri, final RESTListener<T> restListener, Response.Listener responseListener) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RESTUrl + uri, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    sendError(error, restListener);
                }
            });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
     * Send the occurred error: write an error message into log and call listener.onError()
     * @param error the occurred error
     */
    protected void sendError(Exception error, RESTListener<T> listener) {
        Log.e("RESTHelper", error.toString() + " " + error.getMessage());
        listener.onError();
    }
}
