package lifemonitor.application.controller.user_config;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import lifemonitor.application.DatabaseHandler;
import lifemonitor.application.R;

public class User_config_activity extends FragmentActivity {


    private EditText edit_Fname;
    private EditText edit_Sname;
    private EditText edit_number;
    private EditText edit_mail;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);

        dbHandler = new DatabaseHandler(this);
        this.edit_Fname = (EditText) findViewById(R.id.firstname_editText);
        this.edit_number = (EditText) findViewById(R.id.number_editText);
        this.edit_mail = (EditText) findViewById(R.id.mail_editText);
        this.edit_Sname = (EditText) findViewById(R.id.surname_editText);
        Button save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSaveConfig();
            }
        });
        get_EditText();
        dbHandler.close();
    }

    private void get_EditText() {
        int user_id = dbHandler.get_first_user_id();

        if (user_id != -1) {

            edit_mail.setText("" + dbHandler.Get_user(user_id).getEmail());
            edit_Fname.setText("" + dbHandler.Get_user(user_id).getFirstName());
            edit_Sname.setText("" + dbHandler.Get_user(user_id).getSurname());
            edit_number.setText("" + dbHandler.Get_user(user_id).getPhoneNumber());
        }

    }

    public void onClickSaveConfig() {
        User new_user = new User(1, this.edit_Fname.getText().toString(), this.edit_Sname.getText().toString(),
                this.edit_number.getText().toString(), this.edit_mail.getText().toString());
        dbHandler.Update_user(new_user);
    }
}

