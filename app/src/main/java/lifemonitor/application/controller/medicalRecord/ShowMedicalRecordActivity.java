package lifemonitor.application.controller.medicalRecord;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.R;
import lifemonitor.application.controller.medicalRecord.adapter.MedicalRecordAdapter;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.MedicalRecord;
import lifemonitor.application.model.medicalRecord.MedicalRecordItem;

/**
 * <p>Find Medical Record from REST Service and Display result data in a ListView</p>
 * <p>
 *     Each <code>MedicalRecordItem</code> found is displayed as an item of this ListView<br/>
 *     Add data into <code>MedicalRecordAdapter</code> in order to display each MedicalRecordItem correctly.
 * </p>
 *
 * @author Jonathan Geoffroy
 */
public class ShowMedicalRecordActivity extends Activity {
    private static final int PATIENT_ID = 1;
    /**
     * Adapter of the ListView displayed
     */
    private MedicalRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medical_record);

        // Link the ListView with a specific Adapter
        ListView medicalRecord = (ListView) findViewById(R.id.medicalRecord);
        adapter = new MedicalRecordAdapter(this);
        medicalRecord.setAdapter(adapter);

        medicalRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicalRecordItem item = (MedicalRecordItem) adapter.getItem(position);
                DialogFragment informationDialog = item.getInformation();
                if(informationDialog != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", item);
                    informationDialog.setArguments(bundle);
                    informationDialog.show(getFragmentManager(), "MedicalRecordItemInformation");
                }
            }
        });
        // Find Medical Record information from REST Service
        refreshListView();
    }

    /**
     * Refresh the ListView by asking Medical Record to REST Service. Notify the ListView changed as soon as the REST Request is ended.<br/>
     * Remove old ListView items and add new ones.
     */
    private void refreshListView() {
        MedicalRecord.findMedicalRecordFor(PATIENT_ID, this, new SingleResultRESTListener<MedicalRecord>() {
            @Override
            public void onGetResponse(MedicalRecord medicalRecord) {
                // Remove old items
                adapter.clear();

                // Add new items
                List<MedicalRecordItem> items = new LinkedList<>();
                items.addAll(medicalRecord.getAllergies());
                items.addAll(medicalRecord.getIllnesses());
                items.addAll(medicalRecord.getTreatments());
                adapter.addAll(items);
            }

            @Override
            public void onError() {
                Toast.makeText(ShowMedicalRecordActivity.this, R.string.connexion_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_medical_record, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When user clicked on "refresh" button
            case R.id.refresh_medical_record_list:
                refreshListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}