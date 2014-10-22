package helpers;

import java.util.List;

/**
 * Retrieves the response of an HTTP request provided by RESTHelper.
 *
 * @author Celia and Jonathan on 22/10/14.
 */
public interface Requester<T> {

    /**
     * Callback when the GET request is done.
     * @param response list of requested classes; empty list if no results.
     */
    public void onGetResponse(List<T> response);

    /**
     * Callback when the ADD request is done.
     * @param added the request status : true if added in database, else false.
     */
    public void onAddResponse(boolean added);

    /**
     * Callback when an HTTP error occurred.
     */
    public void onError();
}
