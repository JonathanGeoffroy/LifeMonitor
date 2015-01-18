package lifemonitor.application.controller.user_config;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import lifemonitor.application.R;

/**
 * Created by exo on 18/01/15.
 */
public class User_config_activity extends FragmentActivity {

    private TextView tt;
    private Button save_button;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);

        dbHandler = new DatabaseHandler(this);
        this.save_button = (Button) findViewById(R.id.save_button);


       // ArrayList<ArrayList<String>> studentList =  sql.getAllUsers();
        System.out.println("waza!");
        //System.out.println(studentList);
        tt =(TextView) findViewById(R.id.textView);

        tt.setText("coucou");

        //User u = new User(1, "max", "0235", "max@gmail.com");
        //dbHandler.Add_Contact(u);
        ArrayList<User> users = dbHandler.Get_users();
        for (User l : users){
            System.out.println(l.getName());
        }
       dbHandler.close();
    }
}
