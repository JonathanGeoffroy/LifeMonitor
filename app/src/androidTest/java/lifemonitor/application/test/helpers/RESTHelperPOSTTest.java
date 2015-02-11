package lifemonitor.application.test.helpers;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.medicalRecord.DangerLevel;
import lifemonitor.application.model.medicalRecord.HowToTake;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Shape;
import lifemonitor.application.model.medicalRecord.Treatment;
import lifemonitor.application.test.controllers.AbstractTestCase;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class RESTHelperPOSTTest extends AbstractTestCase {

    public void testAddTreatment() {
        final CountDownLatch signal = new CountDownLatch(1);

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(getContext());
        Medicine m = new Medicine(1, "doliprane", Shape.PILLS, HowToTake.ORAL, DangerLevel.LEVEL1);
        Calendar c = Calendar.getInstance();
        c.set(2014, 11, 26, 1, 0, 0);
        Date d = c.getTime();
        final Treatment t = new Treatment(d, 8, 1.0, 12, m);
        t.setId(1);
        restHelper.sendPOSTRequest(t, "/treatments", Treatment.class, new PostListener<Treatment>() {
            @Override
            public void onSuccess(Treatment addedObject) {
                Log.v("Treatment.equals", "addedObject.id = " + addedObject.getId());
                assertEquals("The treatment added and received are the same", t, addedObject);
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
