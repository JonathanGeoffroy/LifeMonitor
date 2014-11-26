package lifemonitor.application.helper.rest.listeners;

/**
 * Listener for RestHelper ADD request
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface AddListener<T> {
    /**
     * Called when the POST request succeeds
     * @param addedObject the object added in REST Service
     */
    void onSuccess(T addedObject);

    /**
     * Called when the POST request failed
     */
    void onError();
}
