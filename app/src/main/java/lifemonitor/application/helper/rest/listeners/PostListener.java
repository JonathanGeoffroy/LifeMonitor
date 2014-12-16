package lifemonitor.application.helper.rest.listeners;

/**
 * Listener for RestHelper ADD request
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface PostListener<T> extends RESTListener<T> {
    /**
     * Called when the POST request succeeds
     * @param addedObject the object added in REST Service
     */
    void onSuccess(T addedObject);
}
