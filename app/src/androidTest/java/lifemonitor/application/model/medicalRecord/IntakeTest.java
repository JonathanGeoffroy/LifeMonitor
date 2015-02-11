package lifemonitor.application.model.medicalRecord;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;


public class IntakeTest extends TestCase {

    private Intake createIntake(int hour, int minute) {
        Intake intake = new Intake();
        intake.setId(0);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.set(Calendar.HOUR_OF_DAY, hour);
        timeCalendar.set(Calendar.MINUTE, minute);
        timeCalendar.set(Calendar.SECOND, 0);
        timeCalendar.set(Calendar.MILLISECOND, 0);
        Date time = timeCalendar.getTime();
        intake.setTime(time);

        return intake;
    }

    public void testIsBetween() throws Exception {
        Intake intake = createIntake(8, 0);
        Date time = intake.getTime();
        Date first = new Date(time.getTime() - 1000);
        Date second = new Date(time.getTime() + 1000);
        assertTrue(intake.isBetween(first, second));
    }

    public void testIsNotBetweenWithExpiredIntake() throws Exception {
        Intake intake = createIntake(8, 0);
        Date time = intake.getTime();
        Date first = new Date(time.getTime() + 1000);
        Date second = new Date(time.getTime() + 2 * 1000);
        assertFalse(intake.isBetween(first, second));
    }

    public void testIsNotBetweenWithNotYetToTakeIntake() throws Exception {
        Intake intake = createIntake(8, 0);
        Date time = intake.getTime();
        Date first = new Date(time.getTime() - 2 * 1000);
        Date second = new Date(time.getTime() - 1000);
        assertFalse(intake.isBetween(first, second));
    }

    public void testIsExpiredWithNotExpiredIntake() throws Exception {
        Intake intake = new Intake();
        intake.setTime(Calendar.getInstance().getTime());
        assertFalse(intake.isExpired());
    }

    public void testIsExpiredWithExpiredIntake() throws Exception {
        Intake intake = new Intake();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, - Intake.NB_MS_BEFORE_EXPIRATION);
        intake.setTime(calendar.getTime());
        assertTrue(intake.isExpired());
    }
}