package lifemonitor.application;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import lifemonitor.application.controller.medicalRecord.AddTreatmentActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicalRecordActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicineActivity;
import lifemonitor.application.controller.userconfig.UserConfigActivity;

public class MyActivity extends Activity {

    private DatabaseHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DatabaseHandler(this);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dashboard_layout);

        /**
         * Creating all buttons instances
         */
        // Dashboard Medical record button
        Button btn_medicalRecord = (Button) findViewById(R.id.btn_showmedicalrecord);

        // Dashboard Add treatment button
        Button btn_addtreatment = (Button) findViewById(R.id.btn_addtreatment);

        // Dashboard show medicine button
        Button btn_showmedicine = (Button) findViewById(R.id.btn_showmedicine);

        // Dashboard show configuration button
        Button btn_showconfig = (Button) findViewById(R.id.btn_showconfig);

        // Dashboard show configuration button
        Button btn_call = (Button) findViewById(R.id.btn_call);


        /**
         * Handling all button click events
         */
        // Listening to Show Medical Record button click
        btn_medicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowMedicalRecordActivity.class);
                startActivity(intent);
            }
        });

        // Listening to Add treatment button click
        btn_addtreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTreatmentActivity.class);
                startActivity(i);
            }
        });

        // Listening show medicine button click
        btn_showmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShowMedicineActivity.class);
                i.putExtra("medicineId",1);
                startActivity(i);
            }
        });

        // Listening show configuration button click
        btn_showconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserConfigActivity.class);
                startActivity(i);
            }
        });

        // Make a phone call
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String call = dbHandler.getUser(dbHandler.getFirstUserId()).getEmergencyNumber();
                if (!call.equals("")) {
                    callIntent.setData(Uri.parse("tel:"+call));
                    startActivity(callIntent);
                }
                else {
                    Toast.makeText(MyActivity.this, R.string.emergency_number_empty, Toast.LENGTH_LONG).show();
                }
            }
        });

        dbHandler.close();
    }
}

