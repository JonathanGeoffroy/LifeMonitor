package lifemonitor.application.controller.userconfig;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lifemonitor.application.DatabaseHandler;
import lifemonitor.application.R;
import lifemonitor.application.model.User;

/**
 * Activity which able user to define his personal data
 */
public class UserConfigActivity extends FragmentActivity {

    private EditText editFirstName;
    private EditText editSurname;
    private EditText editNumber;
    private EditText editMail;
    private EditText editBlood;
    private EditText editUrgency;
    private EditText editDrName;
    private EditText editDrNumber;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);

        dbHandler = new DatabaseHandler(this);
        this.editFirstName = (EditText) findViewById(R.id.firstname_editText);
        this.editNumber = (EditText) findViewById(R.id.number_editText);
        this.editMail = (EditText) findViewById(R.id.mail_editText);
        this.editSurname = (EditText) findViewById(R.id.surname_editText);
        this.editBlood = (EditText) findViewById(R.id.user_bloodgroup_editText);
        this.editUrgency = (EditText) findViewById(R.id.user_urgencyNumber_editText);
        this.editDrName = (EditText) findViewById(R.id.user_drname_editText);
        this.editDrNumber = (EditText) findViewById(R.id.user_drNumber_editText);
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSaveConfig();
            }
        });
        getEditText();
        dbHandler.close();
    }


    /**
     * Fill the input with value in the database
     */
    private void getEditText() {
        int user_id = dbHandler.getFirstUserId();

        if (user_id != -1) {
            editMail.setText("" + dbHandler.getUser(user_id).getEmail());
            editFirstName.setText("" + dbHandler.getUser(user_id).getFirstName());
            editSurname.setText("" + dbHandler.getUser(user_id).getSurname());
            editNumber.setText("" + dbHandler.getUser(user_id).getPhoneNumber());
            editBlood.setText("" + dbHandler.getUser(user_id).getBloodGroup());
            editUrgency.setText("" + dbHandler.getUser(user_id).getUrgencyNumber());
            editDrName.setText("" + dbHandler.getUser(user_id).getDrName());
            editDrNumber.setText("" + dbHandler.getUser(user_id).getDrNumber());

        }

    }

    /**
     * Save the user configuration
     */
    public void onClickSaveConfig() {
        User new_user = new User(1, this.editFirstName.getText().toString(), this.editSurname.getText().toString(),
                this.editNumber.getText().toString(), this.editMail.getText().toString(), this.editBlood.getText().toString(),
        this.editUrgency.getText().toString(), this.editDrName.getText().toString(), this.editDrNumber.getText().toString());
        dbHandler.updateUser(new_user);
        this.finish();
    }
}

