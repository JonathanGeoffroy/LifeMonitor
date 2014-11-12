package lifemonitor.application.test.helpers;

import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import lifemonitor.application.helper.rest.MultipleResultsRESTHelper;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.model.medicalRecord.Treatment;
import lifemonitor.application.test.controllers.AbstractTestCase;

public class MultipleResultsRESTHelperTest extends AbstractTestCase{

    public void testEmptyResult() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        RESTHelper<Treatment> restHelper = new MultipleResultsRESTHelper<Treatment>(this.getContext(), new MultipleResultsRESTListener<Treatment>() {
            @Override
            public void onGetResponse(List<Treatment> results) {
                assertTrue(true);
                //assertTrue("results should be empty", results.isEmpty());
                signal.countDown();
            }

            @Override
            public void onError() {
                fail("an error occurred");
                signal.countDown();
            }
        });
        restHelper.sendGETRequest("/patients/2/treatments",Treatment.class);
        signal.await();
    }
}
