package lifemonitor.application.model.medicalRecord;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lifemonitor.application.controller.medicalRecord.adapter.items.TodayTreatmentItem;

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

    public void testFindTodayDosesForFinishedTreatment() throws Exception {
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.add(Calendar.DAY_OF_MONTH, -2);
        Treatment treatment = new Treatment(oldCalendar.getTime(), 1, 1., 1, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);
        assertTrue("An already finished treatment should not appear into medicines to take today", items.isEmpty());
    }

    public void testFindTodayDosesForNotYetStartedTreatment() throws Exception {
        Calendar notYetStartedCalendar = Calendar.getInstance();
        notYetStartedCalendar.add(Calendar.DAY_OF_MONTH, 1);
        Treatment treatment = new Treatment(notYetStartedCalendar.getTime(), 1, 1., 1, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);

        assertTrue("A not yet started treatment should not appear into medicines to take today", items.isEmpty());
    }

    public void testFindTodayDosesForCurrentlyTakenTreatmentStartingToday() throws Exception {
        Calendar treatmentCalendar = Calendar.getInstance();
        treatmentCalendar.set(Calendar.HOUR_OF_DAY, 8);
        treatmentCalendar.set(Calendar.MINUTE, 30);
        Treatment treatment = new Treatment(treatmentCalendar.getTime(), 24, 1., 1, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);

        assertEquals("This treatment should provide one medicine to take", 1, items.size());
        assertEquals("The dose should be taken at the time of Treatment creation", "08:30", items.get(0).getDate());
    }

    public void testFindTodayDosesForCurrentlyTakenTreatmentStartingYesterday() throws Exception {
        Calendar treatmentCalendar = Calendar.getInstance();
        treatmentCalendar.add(Calendar.DAY_OF_MONTH, -1);
        treatmentCalendar.set(Calendar.HOUR_OF_DAY, 8);
        treatmentCalendar.set(Calendar.MINUTE, 30);

        Treatment treatment = new Treatment(treatmentCalendar.getTime(), 24, 1., 3, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);

        assertEquals("This treatment should provide one medicine to take", 1, items.size());
        assertEquals("The dose should be taken at the time of Treatment creation", "08:30", items.get(0).getDate());
    }

    public void testFindTodayDosesForCurrentlyTakenTreatmentThreeDosesPerDay() throws Exception {
        Calendar treatmentCalendar = Calendar.getInstance();
        treatmentCalendar.set(Calendar.HOUR_OF_DAY, 12);
        treatmentCalendar.set(Calendar.MINUTE, 30);
        Treatment treatment = new Treatment(treatmentCalendar.getTime(), 8, 1., 1, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);

        assertEquals("This treatment should provide three medicines to take", 3, items.size());
        assertEquals("The first dose should be taken at 4:30", "04:30", items.get(0).getDate());
        assertEquals("The first dose should be taken at 12:30", "12:30", items.get(1).getDate());
        assertEquals("The first dose should be taken at 20:30", "20:30", items.get(2).getDate());
    }

    public void testFindTodayDosesForADosePerWeekButToTakeToday() throws Exception {
        Calendar treatmentCalendar = Calendar.getInstance();
        treatmentCalendar.add(Calendar.DAY_OF_MONTH, -7);
        treatmentCalendar.set(Calendar.HOUR_OF_DAY, 12);
        treatmentCalendar.set(Calendar.MINUTE, 30);
        Treatment treatment = new Treatment(treatmentCalendar.getTime(), 168, 1., 14, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);

        assertEquals("This treatment should provide three medicines to take", 1, items.size());
        assertEquals("The first dose should be taken at 12:30", "12:30", items.get(0).getDate());
    }

    public void testFindTodayDosesForADosePerWeekButNotToTakeToday() throws Exception {
        Calendar treatmentCalendar = Calendar.getInstance();
        treatmentCalendar.add(Calendar.DAY_OF_MONTH, -5);
        treatmentCalendar.set(Calendar.HOUR_OF_DAY, 12);
        treatmentCalendar.set(Calendar.MINUTE, 30);
        Treatment treatment = new Treatment(treatmentCalendar.getTime(), 168, 1., 14, new Medicine(0), null);
        List<TodayTreatmentItem> items = new ArrayList<>();
        treatment.findTodayDoses(items);

        assertTrue("This treatment should NOT provide any dose to take", items.isEmpty());
    }
}