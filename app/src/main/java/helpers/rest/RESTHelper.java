package helpers.rest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import helpers.rest.listeners.RESTListener;

/**
 * Sends HTTP requests to REST service, parses JSON result to get a list of T objects and gives this list to the Requester.
 *
 * @author Celia and Jonathan on 15/10/14.
 */
public abstract class RESTHelper<T> {

    /**
     * REST service address.
     */
    private static String RESTUrl = "http://glefer.fr:9000/app_dev.php"; //TODO : give real prod address.
    private final RESTListener<T> listener;

    public static void setRESTUrl(String RESTUrl) {
        RESTHelper.RESTUrl = RESTUrl;
    }

    protected Context context;

    public RESTHelper(Context context, RESTListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Sends a GET request.
     * @param uri uri, begins with "/" (for example "/treatment"), will be concatenated with RESTUrl.
     * @param clazz class of objects to get.
     */
    public void sendGETRequest(String uri, final Class<T> clazz) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RESTUrl + uri,
            new Response.Listener() {

                @Override
                public void onResponse(Object o) {
                    String result = (String) o;
                    Log.v("RESTHelper", result);
                    // Parses the result to get a list of objects
                    parseResult(result, clazz);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("RESTHelper", error.toString() + " " + error.getMessage());
                    listener.onError();
                }
            });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    protected abstract void parseResult(String result, Class<T> clazz);
}
