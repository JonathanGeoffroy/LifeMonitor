package helpers.rest.listeners;

import java.util.List;

import helpers.rest.listeners.RESTListener;

/**
 * Specific Listener for RESTHelper which returns single result as a T object
 *
 * @see helpers.rest.SingleResultRESTHelper
 *
 * @author Jonathan Geoffroy
 */
public abstract class SingleResultRESTListener<T> implements RESTListener<T> {
    /**
     * Called when REST request succeeded
     * @param result the result of the REST request
     */
    public abstract void onGetResponse(T result);
}
