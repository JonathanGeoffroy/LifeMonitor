package lifemonitor.application.database;

import android.content.Context;

import java.util.List;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.Patient;
import lifemonitor.application.model.medicalRecord.Doctor;

/**
 * Provides few method to get tuple from remote database
 *
 * @author Romain Philippon
 */
public class RemoteDataBase {
    private Context context;
    private Patient p;
    private Doctor doc;
    private List<Patient> listPatient;
    private List<Doctor> listDoctor;


    public RemoteDataBase(Context context) {
        this.context = context;
    }

    public Doctor getDoctor(int doctorID) {
        final String request = String.format("/doctors/%d", doctorID);

        new RESTHelper<Doctor>(this.context).sendGETRequestForSingleResult(request, Doctor.class, new SingleResultRESTListener<Doctor>() {
            @Override
            public void onGetResponse(Doctor result) {
                RemoteDataBase.this.doc = result;
            }

            @Override
            public void onError() {
                RemoteDataBase.this.doc = null;
            }
        });

        if(this.doc != null) {
            return this.doc;
        }
        else {
            throw new IllegalArgumentException("Impossible to retrieve a doctor with the id "+ doctorID);
        }
    }

    /*
    public Patient getPatient(int patientID) {
        final String request = String.format("/doctors/%d", patientID);

        new RESTHelper<Patient>(this.context).sendGETRequestForSingleResult(request, Patient.class, new SingleResultRESTListener<Patient>() {
            @Override
            public void onGetResponse(Patient result) {
                RemoteDataBase.this.p = result;
            }

            @Override
            public void onError() {
                RemoteDataBase.this.p = null;
            }
        });

        if( RemoteDataBase.this.p != null) {
            return  RemoteDataBase.this.p;
        }
        else {
            throw new IllegalArgumentException("Impossible to retrieve a patient with the id "+ patientID);
        }
    }
    */
}
