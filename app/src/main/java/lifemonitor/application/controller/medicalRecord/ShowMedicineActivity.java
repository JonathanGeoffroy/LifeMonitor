package lifemonitor.application.controller.medicalRecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import lifemonitor.application.R;
import lifemonitor.application.model.medicalRecord.DangerLevel;
import lifemonitor.application.model.medicalRecord.HowToConsume;
import lifemonitor.application.model.medicalRecord.Medicine;
import lifemonitor.application.model.medicalRecord.Shape;

public class ShowMedicineActivity extends FragmentActivity{

    Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine);

        TextView name=(TextView)findViewById(R.id.medicine_name_value);
        TextView shape=(TextView)findViewById(R.id.medicine_shape_value);
        TextView howToConsume=(TextView)findViewById(R.id.medicine_consumption_value);
        ImageView dangerLevel=(ImageView)findViewById(R.id.medicine_dangerlevel_value);

        Intent intent=getIntent();


        /*this part should use resthelper*/

        /*String medicine_name=intent.getStringExtra("MedicineName");
        *
        *
        *
        *
        *
        * */

        Medicine medicine=getMockSample();

        name.setText(medicine.getName());

        shape.setText(medicine.getShape().resource(this));
        howToConsume.setText(medicine.getHowToConsume().resource(this));
        String dangerLevelEnumValue =medicine.getDangerLevel().toString();

        if ( "LEVEL1".equals(dangerLevelEnumValue)){
            dangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.conducteurlevel1));
        }
        else if( "LEVEL2".equals(dangerLevelEnumValue)){
            dangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.conducteurlevel2));
        }
        else if( "LEVEL3".equals(dangerLevelEnumValue)){
            dangerLevel.setImageDrawable(getResources().getDrawable(R.drawable.conducteurlevel3));
        }
        else{System.out.println("exception ? NOT !");}


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_medicine, menu);
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

    private Medicine getMockSample() {
        return new Medicine("Chocolate", Shape.POWDER, HowToConsume.ORALE, DangerLevel.LEVEL3);
    }
}
