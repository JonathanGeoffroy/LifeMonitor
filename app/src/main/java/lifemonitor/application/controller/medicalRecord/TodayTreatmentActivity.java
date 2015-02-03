package lifemonitor.application.controller.medicalRecord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.TodayTreatmentAdapter;
import lifemonitor.application.controller.medicalRecord.adapter.items.TodayTreatmentItem;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.MedicalRecord;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * Display for each medicine, the list of doses to take today.
 * Display the treatment, the date of the dose, and a switch to accept the dose.
 */
public class TodayTreatmentActivity extends Fragment {


    private static final int MILLISECONDS_PER_HOUR = 1000 * 60 * 60;
    private static final int MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * 24;

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
                List<TodayTreatmentItem> list = findTreatmentItems(medicalRecord);
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

    /**
     * Find doses to take today by a patient, by exploring its medical record.
     * For each treatment, check if it has to take today, and compute each dose to take today.
     * @param medicalRecord the medical record which contains all treatments
     * @return the list of TodayTreatmentItem to take today
     */
    private List<TodayTreatmentItem> findTreatmentItems(MedicalRecord medicalRecord) {
        List<TodayTreatmentItem> items = new ArrayList<>();
        // Today at midnight
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        // Tomorrow at midnight
        Calendar tomorrowCalendar = Calendar.getInstance();
        tomorrowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowCalendar.set(Calendar.MINUTE, 0);
        tomorrowCalendar.set(Calendar.SECOND, 0);
        tomorrowCalendar.set(Calendar.MILLISECOND, 0);
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = tomorrowCalendar.getTime();

        for(Treatment treatment: medicalRecord.getTreatments()) {
            Date endDate = new Date(treatment.getDate().getTime() + treatment.getDuration() * MILLISECONDS_PER_DAY);

            // If treatments have to be taken today
            if(treatment.getDate().before(tomorrow) && endDate.after(todayCalendar.getTime())) {
                Calendar treatmentDate = new GregorianCalendar();
                treatmentDate.setTime(treatment.getDate());

                int frequency = treatment.getFrequency();

                // Compute the date of the first dose to take today
                // It corresponds to hour of treatment.getDate(), but with today's day
                Calendar nextDoseCalendar = Calendar.getInstance();
                nextDoseCalendar.set(Calendar.HOUR_OF_DAY, 0);
                nextDoseCalendar.set(Calendar.MINUTE, 0);
                nextDoseCalendar.set(Calendar.SECOND, 0);
                Date nextDose = nextDoseCalendar.getTime();

                // Compute all doses to take today
                while (nextDose.before(tomorrow)) {
                    items.add(new TodayTreatmentItem(treatment, nextDose));
                    nextDose = new Date(nextDose.getTime() + frequency * MILLISECONDS_PER_HOUR);
                }
            }
        }

        return items;
    }
}
