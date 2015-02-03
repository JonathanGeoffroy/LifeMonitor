package lifemonitor.application.controller.medicalRecord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.TodayTreatmentAdapter;
import lifemonitor.application.controller.medicalRecord.adapter.items.TodayTreatmentItem;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.MedicalRecord;

/**
 * Display for each medicine, the list of doses to take today.
 * Display the treatment, the date of the dose, and a switch to accept the dose.
 */
public class TodayTreatmentActivity extends Fragment {

    public TodayTreatmentActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_today_treatment, container, false);

        // Link the ListView with a specific Adapter
        ListView medicalRecord = (ListView) rootView.findViewById(R.id.treatments_listview);
        final TodayTreatmentAdapter adapter = new TodayTreatmentAdapter(rootView.getContext());

        MedicalRecord.findMedicalRecordFor(1, getActivity(), new SingleResultRESTListener<MedicalRecord>() {
            @Override
            public void onGetResponse(MedicalRecord medicalRecord) {
                List<TodayTreatmentItem> list = medicalRecord.findTreatmentItems();
                adapter.addAll(list);
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
