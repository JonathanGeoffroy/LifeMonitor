package lifemonitor.application.model.medicalRecord;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.controller.medicalRecord.adapter.TodayIntakeAdapter;
import lifemonitor.application.controller.medicalRecord.adapter.items.TodayIntakeItem;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.MultipleResultsRESTListener;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;

/**
 * @author Jonathan Geoffroy
 */
public class MedicalRecord {
    private int id;
    private List<Allergy> allergies;
    private List<Illness> illnesses;

    private List<Treatment> treatments;

    public MedicalRecord() {
        treatments = new LinkedList<>();
    }

    /**
     * Find the Medical Record of a patient identified by his ID.
     * Retrieve allergies, illnesses & treatments of this patient by asking to the REST Service.
     * Call <code>onResponse.onGetResponse</code> with the Medical Record in case of success,
     * <code>onResponse.onError</code> otherwise
     * @param patientId the id of hte patient to find
     * @param context the context which asks for the Patient Record
     * @param onResponse a listener called when Entire Medical Record is found
     */
    public static void findMedicalRecordFor(final int patientId, final Context context, final SingleResultRESTListener<MedicalRecord> onResponse) {
        RESTHelper<MedicalRecord> medicalRecordRestHelper = new RESTHelper<>(context);
        medicalRecordRestHelper.sendGETRequestForSingleResult("/patients/" + patientId + "/file", MedicalRecord.class, new SingleResultRESTListener<MedicalRecord>() {
            @Override
            public void onGetResponse(MedicalRecord medicalRecord) {
                medicalRecord.findTreatments(patientId, context, onResponse);
            }

            @Override
            public void onError() {
                onResponse.onError();
            }
        });
    }

    /**
     * Run a GET request to the REST Service in order to find treatments of a patient, identified by his id.
     * @param patientId the id of hte patient to find
     * @param context the context which asks for the Patient Record
     * @param onResponse a listener called when Entire Medical Record is found
     */
    private void findTreatments(int patientId, Context context, final SingleResultRESTListener<MedicalRecord> onResponse) {
        RESTHelper<Treatment> treatmentRESTHelper = new RESTHelper<>(context);
        treatmentRESTHelper.sendGETRequestForMultipleResults("/files/" + patientId + "/treatments", Treatment.class, new MultipleResultsRESTListener<Treatment>() {
            @Override
            public void onGetResponse(List<Treatment> results) {
                treatments.addAll(results);
                onResponse.onGetResponse(MedicalRecord.this);
            }

            @Override
            public void onError() {
                onResponse.onError();
            }
        });
    }

    /**
     * Find doses to take today by a patient, by exploring its medical record.
     * For each current treatment, ask today intakes to REST Helper.
     * Transform each Intake into TodayIntakeItem for convenience.
     * @param context context where to run  REST Service requests
     * @param adapter where to add results asynchronously
     */
    public void findTreatmentItems(Context context, final TodayIntakeAdapter adapter) throws InterruptedException {
        // Today at midnight
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        final Date today = todayCalendar.getTime();

        // Tomorrow at midnight
        Calendar tomorrowCalendar = Calendar.getInstance();
        tomorrowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowCalendar.set(Calendar.MINUTE, 0);
        tomorrowCalendar.set(Calendar.SECOND, 0);
        tomorrowCalendar.set(Calendar.MILLISECOND, 0);
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1);
        final Date tomorrow = tomorrowCalendar.getTime();

        // For each treatment, find all doses to take today
        for(final Treatment treatment: treatments) {
            if(treatment.isCurrent()) {
                RESTHelper<Intake> restHelper = new RESTHelper<>(context);
                restHelper.sendGETRequestForMultipleResults(String.format("/treatments/%d/intakes/full", treatment.getId()), Intake.class, new MultipleResultsRESTListener<Intake>() {
                    @Override
                    public void onGetResponse(List<Intake> intakes) {
                        for (Intake intake : intakes) {
                            if (intake.isBetween(today, tomorrow)) {
                                adapter.add(new TodayIntakeItem(intake, treatment));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {
                        Log.e("MedicalRecord.findTreatmentItems", "Error occurred");
                    }
                });
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }
}
