package lifemonitor.application.model.medicalRecord;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

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
        treatments = new LinkedList<Treatment>();
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
        RESTHelper<MedicalRecord> medicalRecordRestHelper = new RESTHelper<MedicalRecord>(context);
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
        RESTHelper<Treatment> treatmentRESTHelper = new RESTHelper<Treatment>(context);
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
