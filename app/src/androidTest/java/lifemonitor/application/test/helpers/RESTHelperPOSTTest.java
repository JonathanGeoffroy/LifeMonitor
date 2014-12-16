package lifemonitor.application.test.helpers;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Treatment;
import lifemonitor.application.test.controllers.AbstractTestCase;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class RESTHelperPOSTTest extends AbstractTestCase {

    public void testAddTreatment() {
        final CountDownLatch signal = new CountDownLatch(1);

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(getContext());
        Medicine m = new Medicine(1);
        Treatment t = new Treatment(new Date(), "2 fois par jour", 12, m);
        restHelper.sendPOSTRequest(t, "/treatments/", Treatment.class, new PostListener<Treatment>() {
            @Override
            public void onSuccess(Treatment addedObject) {
                assertNotNull(addedObject);
                signal.countDown();
            }

            @Override
            public void onError() {
                fail("Should return the object you added : network connection problem ?");
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
