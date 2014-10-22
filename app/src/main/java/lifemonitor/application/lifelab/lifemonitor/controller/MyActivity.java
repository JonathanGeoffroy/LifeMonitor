package lifemonitor.application.lifelab.lifemonitor.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import lifemonitor.application.lifelab.lifemonitor.R;
import lifemonitor.application.lifelab.lifemonitor.controller.medicalRecord.AddTreatmentActivity;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        /* Button to add a treatment */
        Button button = (Button) findViewById(R.id.addTreatmentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddTreatment();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Launches the activity that enables to add a treatment.
     */
    public void onClickAddTreatment() {
        Intent intent = new Intent(this, AddTreatmentActivity.class);
        startActivity(intent);
    }
}
