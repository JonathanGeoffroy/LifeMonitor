package lifemonitor.application.controller.medicalRecord.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
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
        builder.setView(R.layout.dialog_add_genetic_desease);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
