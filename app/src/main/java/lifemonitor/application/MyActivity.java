package lifemonitor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import lifemonitor.application.controller.medicalRecord.AddTreatmentActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicineActivity;
import lifemonitor.application.controller.user_config.User_config_activity;

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

        Button button_user_config = (Button) findViewById(R.id.showConfigMenu);
        button_user_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUserConfig();
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
        intent.putExtra("MedicineName", "basic");
        startActivity(intent);
    }

    /**
     * Launches the activity to config lifemonitor.
     */
    public void onClickUserConfig() {
        Intent intent = new Intent(this, User_config_activity.class);
        startActivity(intent);
    }
}

