package lifemonitor.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import lifemonitor.application.controller.user_config.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "usersManager";

    // users table name
    private static final String TABLE_USERS = "users";

    // users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_SNAME = "sname";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private final ArrayList<User> user_list = new ArrayList<User>();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        there_can_be_only_one();
        if (is_empty()) {
            init_empty();
        }
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FNAME + " TEXT,"
                 +KEY_SNAME + " TEXT," + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new user
    public void Add_user(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, user.getFirstName()); // user FName
        values.put(KEY_SNAME, user.getSurname()); // user FName
        values.put(KEY_PH_NO, user.getPhoneNumber()); // user Phone
        values.put(KEY_EMAIL, user.getEmail()); // user Email
        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single user
    public User Get_user(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_FNAME, KEY_SNAME, KEY_PH_NO, KEY_EMAIL}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
        // return user
        cursor.close();
        db.close();

        return user;
    }

    // Getting All users
    public ArrayList<User> Get_users() {
        try {
            user_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_USERS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setID(Integer.parseInt(cursor.getString(0)));
                    user.setFirstName(cursor.getString(1));
                    user.setSurname(cursor.getString(2));
                    user.setPhoneNumber(cursor.getString(3));
                    user.setEmail(cursor.getString(4));
                    // Adding user to list
                    user_list.add(user);
                } while (cursor.moveToNext());
            }

            // return user list
            cursor.close();
            db.close();
            return user_list;
        } catch (Exception e) {
            Log.e("all_user", "" + e);
        }

        return user_list;
    }

    // Updating single user
    public int Update_user(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, user.getFirstName());
        values.put(KEY_SNAME, user.getSurname());
        values.put(KEY_PH_NO, user.getPhoneNumber());
        values.put(KEY_EMAIL, user.getEmail());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
    }

    // Deleting single user
    public void Delete_user(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void init_empty() {
        String lost = "";
        User user = new User(1,lost, lost, lost, lost);
        this.Add_user(user);

    }

    public boolean is_empty() {//TODO : refonte -> peut être optimisé
        return Get_users().isEmpty();
    }

    /**
     *   Get first user ID
     */
    public int get_first_user_id() {
        if (is_empty()) {
            return -1;
        } else {
            return Get_users().get(0).getID();
        }

    }

    /**
     *  delete all users except the first
     */
    public void there_can_be_only_one() {
        ArrayList<User> user_list = Get_users();
        int first = get_first_user_id();

        for (User l : user_list) {
            if(l.getID()!=first)
            Delete_user(l.getID());
        }
    }

}
