package lifemonitor.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import lifemonitor.application.controller.medicalRecord.AddTreatmentActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicalRecordActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicineActivity;
import lifemonitor.application.controller.userconfig.UserConfigActivity;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dashboard_layout);

        /**
         * Creating all buttons instances
         * */
        // Dashboard Add treatment button
        Button btn_addtreatment = (Button) findViewById(R.id.btn_addtreatment);

        // Dashboard show medicine button
        Button btn_showmedicine = (Button) findViewById(R.id.btn_showmedicine);

        // Dashboard show configuration button
        Button btn_showconfig = (Button) findViewById(R.id.btn_showconfig);

        Button btn_medicalRecord = (Button) findViewById(R.id.btn_showmedicalrecord);

        /**
         * Handling all button click events
         * */

        // Listening to Add treatment button click
        btn_addtreatment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), AddTreatmentActivity.class);
                startActivity(i);
            }
        });

        // Listening to Show Medical Record button click
        btn_medicalRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowMedicalRecordActivity.class);
                startActivity(intent);
            }
        });

        // Listening show medicine button click
        btn_showmedicine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), ShowMedicineActivity.class);
                i.putExtra("medicineId",1);
                startActivity(i);
            }
        });

        // Listening show configuration button click
        btn_showconfig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), UserConfigActivity.class);
                startActivity(i);
            }
        });
    }
}

