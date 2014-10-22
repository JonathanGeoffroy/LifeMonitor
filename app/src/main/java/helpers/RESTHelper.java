package helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sends HTTP requests to REST service, parses JSON result to get a list of T objects and gives this list to the Requester.
 *
 * @author Celia and Jonathan on 15/10/14.
 */
public class RESTHelper<T> {

    /**
     * REST service address.
     */
    private static String RESTUrl = "http://glefer.fr:9000/app_dev.php"; //TODO : give real prod address.

    public static void setRESTUrl(String RESTUrl) {
        RESTHelper.RESTUrl = RESTUrl;
    }

    /**
     * Sends a GET request.
     * @param requester activity which sent a request.
     * @param uri uri, begins with "/" (for example "/treatment"), will be concatenated with RESTUrl.
     * @param clazz class of objects to get.
     */
    private void sendGETRequest(final ActivityRequester<T> requester, String uri, final Class<T> clazz) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requester);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RESTUrl + uri,
            new Response.Listener() {

                @Override
                public void onResponse(Object o) {
                    String result = (String) o;
                    // Parses the result to get a list of objects
                    List<T> listResult = parseResult(result, clazz);
                    // Gives the response to the requester
                    requester.onGetResponse(listResult);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    requester.onError();
                }
            });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
     * Parses the result to get a list of objects.
     * @param result json result.
     * @param clazz class of objects to get.
     * @return list of clazz objects.
     */
    public List<T> parseResult(String result, Class<T> clazz) {
        List<T> listResult = new ArrayList<T>();

        // Jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Parses the result to get
            listResult = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            Log.e("RESTHelper", String.valueOf(e.getStackTrace()));
        }

        return listResult;
    }
}
