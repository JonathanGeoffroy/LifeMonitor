package lifemonitor.application.helper.rest.listeners;

import java.util.List;

/**
 * Specific Listener for RESTHelper which returns multiple results
 *
 * @see lifemonitor.application.helper.rest.MultipleResultsRESTHelper
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public abstract class MultipleResultsRESTListener<T> implements RESTListener<T> {

    /**
     * Called when REST request succeeded
     * @param results the result of the REST request
     */
    public abstract void onGetResponse(List<T> results);
}
