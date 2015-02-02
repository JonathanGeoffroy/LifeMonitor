package lifemonitor.application.controller.medicalRecord.widget;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.LinkedList;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.DiseaseAdapter;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.Illness;

/**
 * Displays a dialog box where user can add its genetic disease get from the data base using the REST Helper
 *
 * @author Romain Philippon
 */
public class AddGeneticDiseaseDialog extends DialogFragment {
    /**
     * Is the rest helper instance used to get illness name stored in data base
     */
    private RESTHelper<Illness> restHelper;
    /**
     * Is the list of illness name used to populate the spinner generated during the instantiation of an AddGeneticDiseaseDialog object
     */
    private DiseaseAdapter adapter;
    /**
     * Corresponds to user's selected genetic disease from the spinner
     */
    private Illness chosenDisease;
    /**
     * Is the spinner that contains all genetic diseases the user an choose
     */
    private Spinner spinner;

    public static AddGeneticDiseaseDialog newInstance() {
        AddGeneticDiseaseDialog dialog = new AddGeneticDiseaseDialog();
        return dialog;
    }

    public AddGeneticDiseaseDialog() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new DiseaseAdapter(this.getActivity());
        this.restHelper = new RESTHelper<Illness>(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* BUILD DIALOG FRAGMENT */
        this.getDialog().setTitle(R.string.dialog_title_add_genetic_disease);
        final View dialogLayout = inflater.inflate(R.layout.dialog_add_genetic_desease, container, false);

        this.getGeneticDisease();

        /*  CREATE SPINNER */
        this.spinner = (Spinner)dialogLayout.findViewById(R.id.spinner_genetic_list);
        this.spinner.setAdapter(this.adapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // update the user's chosen disease
                AddGeneticDiseaseDialog.this.chosenDisease = (Illness)AddGeneticDiseaseDialog.this.adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing to do
            }
        });

        /* EVENTS ON BUTTONS */
        dialogLayout.findViewById(R.id.confirm_genetic_disease).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AddGeneticDiseaseDialog.this.appendDiseaseToMedicalRecord();
           }
        });

        dialogLayout.findViewById(R.id.cancel_genetic_disease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddGeneticDiseaseDialog.this.dismiss();
            }
        });

        return dialogLayout;
    }

    /**
     * Appends an illness name in order to populate the spinner used in this fragment
     * @param illness is the illness name get from rest helper
     */
    private void populateSpinner(Illness illness) {
        this.adapter.add(illness);
        this.spinner.setAdapter(this.adapter);
    }

    /**
     * Update the user's medical record by adding the selected genetic disease of the spinner
     */
    private void appendDiseaseToMedicalRecord() {
        final int PATIENT_ID = 1; // default id to test lifemonitor
        final String request = "/files/"+ PATIENT_ID +"/illnesses";

        this.restHelper.sendPOSTRequest(this.chosenDisease, request, Illness.class, new PostListener<Illness>() {
            @Override
            public void onSuccess(Illness addedObject) {
                AddGeneticDiseaseDialog.this.dismiss();
            }

            @Override
            public void onError() {
                AddGeneticDiseaseDialog.this.dismiss();
                Toast.makeText(AddGeneticDiseaseDialog.this.getActivity(), R.string.addGeneticDiseaseError, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Gets genetic diseases from database in order to populate the spinner
     */
    public void getGeneticDisease() {
        final String request = "/illnesses/";
        LinkedList<Integer> diseaseID = new LinkedList<>();
        diseaseID.add(21986); // huntington
        diseaseID.add(2528); // diabietes
        diseaseID.add(13654);// haemophilia (A)
        diseaseID.add(20670); // sclerosteosis
        diseaseID.add(23810); // cystic fibrosis
        diseaseID.add(2415); // paraplegia
        diseaseID.add(20761); // sarcoidosis

        for(int id : diseaseID) {
            this.restHelper.sendGETRequestForSingleResult(request + id, Illness.class, new SingleResultRESTListener<Illness>() {
                @Override
                public void onGetResponse(Illness result) {
                    AddGeneticDiseaseDialog.this.populateSpinner(result);
                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
