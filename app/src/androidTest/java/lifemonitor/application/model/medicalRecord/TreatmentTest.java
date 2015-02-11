package lifemonitor.application.model.medicalRecord;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class TreatmentTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testComputeEndDateForADay() throws Exception {
        int duration = 1;
        Calendar todayCalendar = Calendar.getInstance();
        Date today = todayCalendar.getTime();
        Treatment treatment = new Treatment(today, 1, 1.f, duration, new Medicine(0), null);

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.add(Calendar.DAY_OF_MONTH, duration);

        assertEquals("End date of the treatment should be tomorrow at the same time", expectedCalendar.getTime().toString(), treatment.computeEndDate().toString());
    }

    public void testComputeEndDateForTwoDays() throws Exception {
        int duration = 2;
        Calendar todayCalendar = Calendar.getInstance();
        Date today = todayCalendar.getTime();
        Treatment treatment = new Treatment(today, 1, 1.f, duration, new Medicine(0), null);

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.add(Calendar.DAY_OF_MONTH, duration);

        assertEquals("End date of the treatment should be  in two days at the same time", expectedCalendar.getTime().toString(), treatment.computeEndDate().toString());
    }
}