package lifemonitor.application.controller.medicalRecord.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import lifemonitor.application.R;

/**
 * Created by philippon on 03/12/14.
 */
public class AddGeneticDeseaseDialog extends DialogFragment {

    public AddGeneticDeseaseDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_add_genetic_desease, null);

        builder.setView(dialogLayout);

        Spinner spinner = (Spinner)dialogLayout.findViewById(R.id.spinner_genetic_list);

        // TODO : Appends genetic disease in the spinner

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("I'm here");
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
