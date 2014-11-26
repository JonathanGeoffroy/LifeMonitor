package lifemonitor.application.helper.rest.listeners;

/**
 * Specific Listener for RESTHelper which returns single result as a T object
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface SingleResultRESTListener<T> extends RESTListener<T> {
    /**
     * Called when REST request succeeded
     * @param result the result of the REST request
     */
    public void onGetResponse(T result);
}
