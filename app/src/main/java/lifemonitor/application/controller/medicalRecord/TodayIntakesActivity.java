package lifemonitor.application.controller.medicalRecord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.TodayIntakeAdapter;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.MedicalRecord;

/**
 * Display for each medicine, the list of doses to take today.
 * Display the treatment, the date of the dose, and a switch to accept the dose.
 */
public class TodayIntakesActivity extends Fragment {

    public TodayIntakesActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_today_treatment, container, false);

        // Link the ListView with a specific Adapter
        ListView medicalRecord = (ListView) rootView.findViewById(R.id.treatments_listview);
        final TodayIntakeAdapter adapter = new TodayIntakeAdapter(rootView.getContext());

        MedicalRecord.findMedicalRecordFor(1, getActivity(), new SingleResultRESTListener<MedicalRecord>() {
            @Override
            public void onGetResponse(MedicalRecord medicalRecord) {
                try {
                    medicalRecord.findTreatmentItems(getActivity(), adapter);
                } catch (InterruptedException e) {
                    Log.e("medicalRecord.findTreatmentItems", e.getMessage());
                }
            }

            @Override
            public void onError() {
                Toast.makeText(getActivity().getApplicationContext(), R.string.connexion_error, Toast.LENGTH_LONG).show();
            }
        });
        medicalRecord.setAdapter(adapter);
        // Inflate the layout for this fragment
        return rootView;
    }
}
