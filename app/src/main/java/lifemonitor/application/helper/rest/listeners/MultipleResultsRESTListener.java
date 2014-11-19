package lifemonitor.application.helper.rest.listeners;

import java.util.List;

/**
 * Specific Listener for RESTHelper which returns multiple results
 *
 * @see lifemonitor.application.helper.rest.MultipleResultsRESTHelper
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface MultipleResultsRESTListener<T> extends RESTListener<T> {

    /**
     * Called when REST request succeeded
     * @param results the result of the REST request
     */
    public void onGetResponse(List<T> results);
}
