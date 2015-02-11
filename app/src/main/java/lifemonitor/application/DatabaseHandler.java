package lifemonitor.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import lifemonitor.application.model.User;

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
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BLOOD_GROUP = "blood_group";
    private static final String KEY_URGENCY_NUMBER = "urgency_number";
    private static final String KEY_DR_NAME = "dr_name";
    private static final String KEY_DR_NUMBER = "dr_number";
    private final ArrayList<User> user_list = new ArrayList<>();

    /**
     * Main constructor
     * @param context Context of the handler
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        thereCanBeOnlyOne();
        if (isEmpty()) {
            initEmpty();
        }
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRSTNAME + " TEXT,"
                + KEY_SURNAME + " TEXT," + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT,"
                + KEY_BLOOD_GROUP + " TEXT," + KEY_URGENCY_NUMBER + " TEXT," + KEY_DR_NAME + " TEXT,"
                + KEY_DR_NUMBER + " TEXT" + ")";
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

    /**
     * Add a new user in the database
     * @param user user to add in the database
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, user.getFirstName()); // user FName
        values.put(KEY_SURNAME, user.getSurname()); // user FName
        values.put(KEY_PH_NO, user.getPhoneNumber()); // user Phone
        values.put(KEY_EMAIL, user.getEmail()); // user Email
        values.put(KEY_BLOOD_GROUP, user.getEmail());
        values.put(KEY_URGENCY_NUMBER, user.getEmail());
        values.put(KEY_DR_NAME, user.getEmail());
        values.put(KEY_DR_NUMBER, user.getEmail());
        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Get the user identified by the id in parameter
     * @param id id of the user to find
     * @return the user in the local database
     */
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID,
                        KEY_FIRSTNAME, KEY_SURNAME, KEY_PH_NO, KEY_EMAIL, KEY_BLOOD_GROUP, KEY_URGENCY_NUMBER, KEY_DR_NAME, KEY_DR_NUMBER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        // return user
        cursor.close();
        db.close();

        return user;
    }

    /**
     * Get all users in the local database
     * @return list of users in the database
     */
    public ArrayList<User> getUsers() {
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
                    user.setBloodGroup(cursor.getString(5));
                    user.setEmergencyNumber(cursor.getString(6));
                    user.setDrName(cursor.getString(7));
                    user.setDrNumber(cursor.getString(8));

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

    /**
     * Update of the user
     * @param user user to update
     * @return the number of rows affected
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_PH_NO, user.getPhoneNumber());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_BLOOD_GROUP, user.getBloodGroup());
        values.put(KEY_URGENCY_NUMBER, user.getEmergencyNumber());
        values.put(KEY_DR_NAME, user.getDrName());
        values.put(KEY_DR_NUMBER, user.getDrNumber());
        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
    }

    /**
     * Delete the user in the database
     * @param id id of the user to delete
     */
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Initialization of the user
     */
    public void initEmpty() {
        String lost = "";
        User user = new User(1,lost, lost, lost, lost, lost, lost, lost, lost);
        this.addUser(user);

    }


    /**
     * check if the database is empty
     * @return true if the user database is empty
     */
    public boolean isEmpty() {
        return getUsers().isEmpty();
    }

    /**
     *   Get first user ID
     *   @return the first id in the user database
     */
    public int getFirstUserId() {
        if (isEmpty()) {
            return -1;
        } else {
            return getUsers().get(0).getID();
        }

    }

    /**
     * there is only one user in the database
     */
    public void thereCanBeOnlyOne() {
        ArrayList<User> user_list = getUsers();
        int first = getFirstUserId();

        for (User l : user_list) {
            if(l.getID()!=first)
            deleteUser(l.getID());
        }
    }

}