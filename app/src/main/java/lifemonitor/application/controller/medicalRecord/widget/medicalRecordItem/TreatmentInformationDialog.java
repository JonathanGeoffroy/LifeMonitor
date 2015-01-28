package lifemonitor.application.controller.medicalRecord.widget.medicalRecordItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.ShowMedicineActivity;
import lifemonitor.application.model.medicalRecord.Frequency;
import lifemonitor.application.model.medicalRecord.Prescription;
import lifemonitor.application.model.medicalRecord.Treatment;

/**
 * Show a dialog which displays information about a treatment
 * A "medicine" button execute <code>ShowMedicineActivity</code> when user clicks on it.
 *
 * @author Jonathan Geoffroy
 */
public class TreatmentInformationDialog extends DialogFragment {

    /**
     * Intent's key for medicine id
     */
    private static final String MEDICINE_ID = "medicineId";

    /**
     * Displayed Treatment
     */
    private Treatment treatment;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        treatment = (Treatment) args.get("item");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // If this Dialog is restored, retrieve the saved Treatment
        if(savedInstanceState != null && savedInstanceState.containsKey("item")) {
            treatment = (Treatment) savedInstanceState.get("item");
        }

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_treatment_information, null);
        builder.setView(dialogLayout).setPositiveButton("Ok", null);

        // name
        TextView nameTextView = (TextView) dialogLayout.findViewById(R.id.treatment_name);
        nameTextView.setText(treatment.getTitle(getActivity()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/y");
        // start date
        TextView startDateTextView = (TextView) dialogLayout.findViewById(R.id.treatment_startdate);
        startDateTextView.setText(dateFormat.format(treatment.getDate()));

        // end date
        TextView endDateTextView = (TextView) dialogLayout.findViewById(R.id.treatment_enddate);
        endDateTextView.setText(dateFormat.format(treatment.computeEndDate()));

        // quantity
        TextView unitsTextView = (TextView) dialogLayout.findViewById(R.id.treatment_quantity);
        unitsTextView.setText(getQuantity());

        // frequency
        TextView frequencyTextView = (TextView) dialogLayout.findViewById(R.id.treatment_frequency);
        frequencyTextView.setText(getFrequency());

        // prescription
        Prescription prescription = treatment.getPrescription();
        if(prescription == null) {
            // remove "doctor" row from GUI
            TextView doctorMessageTextView = (TextView) dialogLayout.findViewById(R.id.treatment_doctor_message);
            doctorMessageTextView.setText("");
        }
        else {
            // add "doctor" name from GUI
            TextView doctorNameTextView = (TextView) dialogLayout.findViewById(R.id.treatment_doctor_name);
            doctorNameTextView.setText(prescription.getDoctor().getName());
        }

        // Add "medicine Button" which execute ShowMedicineActivity
        ImageButton medicineButton = (ImageButton) dialogLayout.findViewById(R.id.treatment_medicine_button);
        medicineButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowMedicineActivity.class);
                intent.putExtra(MEDICINE_ID, treatment.getMedicine().getId());
                startActivity(intent);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     *
     * @return quantity and shape of the medicine
     */
    private String getQuantity() {
        double quantity = treatment.getQuantity();
        String shape = treatment.getMedicine().getShape().resource(getActivity());
        return String.format("%s %s", quantity, shape);
    }

    /**
     * Compute a human readable frequency
     * @return a human readable frequency
     */
    private String getFrequency() {
        int frequency;
        String possibility;
        Frequency frequencyPossibility;

        int hoursFrequency = treatment.getFrequency();
        if(hoursFrequency == 0) {
            hoursFrequency = 1;
        }

        // Find the better way to write frequency
        if(hoursFrequency >= Frequency.YEAR.getHours()) {
            frequencyPossibility = Frequency.YEAR;
        }
        else if(hoursFrequency >= Frequency.MONTH.getHours()) {
            frequencyPossibility = Frequency.MONTH;
        }
        else if (hoursFrequency >= Frequency.WEEK.getHours()) {
            frequencyPossibility = Frequency.WEEK;
        }
        else {
            frequencyPossibility = Frequency.DAY;
        }

        // Convert frequency in human sentence
        possibility = frequencyPossibility.resource(getActivity());
        frequency = frequencyPossibility.getHours() / hoursFrequency;
        return String.format("%d %s %s", frequency, getResources().getString(R.string.frequency_separator), possibility);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", treatment);
        super.onSaveInstanceState(outState);
    }
}