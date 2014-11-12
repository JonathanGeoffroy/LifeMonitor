package lifemonitor.application.helper.rest.listeners;

/**
 * Listener for REST requests
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface RESTListener<T> {
    /**
     * Called when an error occurs during the REST request
     */
    public void onError();
}
