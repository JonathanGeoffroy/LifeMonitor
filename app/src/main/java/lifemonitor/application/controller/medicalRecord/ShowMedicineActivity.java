package lifemonitor.application.controller.medicalRecord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lifemonitor.application.R;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.medicalRecord.Medicine;

/**
 * Get all information about a medicine in database and display them.<br/>
 * Must get a valid medicine id from intent.
 *
 * @author Celia Cacciatore, Maxime Douylliez
 */
public class ShowMedicineActivity extends Activity {

    /**
     * Name of the medicine
     */
    TextView name;
    /**
     * Shape of the medicine
     */
    TextView shape;
    /**
     * how to consume the medicine
     */
    TextView howToConsume;
    /**
     * Danger level of the medicine
     */
    ImageView dangerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine);

        name = (TextView) findViewById(R.id.medicine_name_value);
        shape = (TextView) findViewById(R.id.medicine_shape_value);
        howToConsume = (TextView) findViewById(R.id.medicine_consumption_value);
        dangerLevel = (ImageView) findViewById(R.id.medicine_dangerlevel_value);

        Intent intent = getIntent();
        int id;
        id = intent.getIntExtra("medicineId", -1);

        // Test : if default value in id, intent hasn't the real medicine id so we don't make request
        if (id == -1) {
            Toast.makeText(ShowMedicineActivity.this, getString(R.string.intent_medicine_id_error), Toast.LENGTH_LONG).show();
        } else {
            // Request to get a medicine object in database with rest helper
            RESTHelper<Medicine> helper = new RESTHelper<Medicine>(this.getBaseContext());
            // TODO : get real id
            helper.sendGETRequestForSingleResult("/medicines/" + id, Medicine.class, new SingleResultRESTListener<Medicine>() {
                @Override
                public void onGetResponse(Medicine medicine) {
                    // Displays name, shape, how to take
                    name.setText(medicine.getName());
                    shape.setText(medicine.getShape().resource(ShowMedicineActivity.this));
                    howToConsume.setText(medicine.getHow_to_take().resource(ShowMedicineActivity.this));

                    // Get image according to danger level
                    String dangerLevelEnumValue = medicine.getDanger_level().toString();
                    Context contextToGetRessources = getApplication().getApplicationContext();
                    int id_drawable = contextToGetRessources.getResources().getIdentifier("conducteur"+dangerLevelEnumValue.toLowerCase(),
                            "drawable",
                            contextToGetRessources.getPackageName());


                    if (id_drawable != 0) {
                        dangerLevel.setImageDrawable(getResources().getDrawable(id_drawable));
                    } else {
                        Log.e("ShowMedicineActivity", "Danger Level enum hasn't been initialized");
                    }
                }

                @Override
                public void onError() {
                    Toast.makeText(ShowMedicineActivity.this, R.string.db_error, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}