package lifemonitor.application.test.helpers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.Treatment;
import lifemonitor.application.test.controllers.AbstractTestCase;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class RESTHelperGETTest extends AbstractTestCase {

    public void testGetMultipleResultTreatments() {
        final CountDownLatch signal = new CountDownLatch(1);

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(getContext());
        restHelper.sendGETRequestForMultipleResults("/files/4/treatments", Treatment.class, new MultipleResultsRESTListener<Treatment>() {
            @Override
            public void onGetResponse(List<Treatment> results) {
                assertEquals(1, results.get(0).getId());
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

    public void testGetMultipleResultTreatmentsWithoutPrescription() {
        final CountDownLatch signal = new CountDownLatch(1);

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(getContext());
        restHelper.sendGETRequestForMultipleResults("/files/4/treatmentsWithoutPrescription", Treatment.class, new MultipleResultsRESTListener<Treatment>() {
            @Override
            public void onGetResponse(List<Treatment> results) {
                assertEquals(1, results.get(0).getId());
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

    public void testGetSingleResultTreatment() {
        final CountDownLatch signal = new CountDownLatch(1);

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(getContext());
        restHelper.sendGETRequestForSingleResult("/files/4/treatments/1", Treatment.class, new SingleResultRESTListener<Treatment>() {
            @Override
            public void onGetResponse(Treatment result) {
                assertEquals(1, result.getId());
                signal.countDown();
            }

            @Override
            public void onError() {
                fail("Should find a treatment : network connection problem ?");
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
