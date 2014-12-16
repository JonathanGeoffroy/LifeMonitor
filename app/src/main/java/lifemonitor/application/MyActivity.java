package lifemonitor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

import lifemonitor.application.controller.medicalRecord.AddTreatmentActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicineActivity;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.PostListener;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Treatment;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        /* Button to add a treatment */
        Button buttonTreatment = (Button) findViewById(R.id.addTreatmentButton);
        buttonTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddTreatment();
            }
        });

        Button buttonMedicine = (Button) findViewById(R.id.showMedicinebutton);
        buttonMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickShowMedicine();
            }
        });

        RESTHelper<Treatment> restHelper = new RESTHelper<Treatment>(this);
        Medicine m = new Medicine(1);
        Treatment t = new Treatment(new Date(), "2 fois par jour", 12, m);
        restHelper.sendPOSTRequest(t, "/files/1/treatments", Treatment.class, new PostListener<Treatment>() {
            @Override
            public void onSuccess(Treatment addedObject) {
                Toast.makeText(MyActivity.this, addedObject.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AddHelper", "-->" + addedObject);
            }

            @Override
            public void onError() {
                Toast.makeText(MyActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("AddHelper", "Error!");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /**
     * Launches the activity that enables to add a treatment.
     */
    public void onClickAddTreatment() {
        Intent intent = new Intent(this, AddTreatmentActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the activity that enables to add a treatment.
     */
    public void onClickShowMedicine() {


            Intent intent = new Intent(this, ShowMedicineActivity.class);
        intent.putExtra("MedicineName","basic");
            startActivity(intent);
        }


}

