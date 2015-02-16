package lifemonitor.application.controller.medicalRecord.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import lifemonitor.application.R;

/**
 * Shows legend which explains what color (in medical record activity) refers to
 *
 * @author Romain Philippon
 */
public class LegendMedicalRecordDialog extends DialogFragment {

    public static LegendMedicalRecordDialog newInstance() {
        LegendMedicalRecordDialog dialog = new LegendMedicalRecordDialog();

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = this.getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(this.getActivity().getString(R.string.dialog_title_legend_medrecord));
        builder.setView(inflater.inflate(R.layout.dialog_legend_medrecord, null));

        return builder.create();
    }


}
