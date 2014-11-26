package lifemonitor.application.test.helpers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.model.medicalRecord.Treatment;
import lifemonitor.application.test.controllers.AbstractTestCase;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class RESTHelperTest extends AbstractTestCase {

    public void testGetMultipleResult() {
        final CountDownLatch signal = new CountDownLatch(1);

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(getContext());
        restHelper. sendGETRequestForMultipleResults("/treatments/all", Treatment.class, new MultipleResultsRESTListener<Treatment>() {
            @Override
            public void onGetResponse(List<Treatment> results) {
                assertNotNull(results);
                assertEquals(1,results.get(0).getId());
                signal.countDown();
            }

            @Override
            public void onError() {
                fail("Should find all treatments : network connection problem ?");
                signal.countDown();
            }
        });
        try {
            signal.await();
        } catch (InterruptedException e) {
            fail("Internal failure");
        }
    }
}
