package lifemonitor.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.model.User;

/**
 * Gathers commons SQLite table's properties
 *
 * @author Romain Philippon
 */
public class LocalDataBase extends SQLiteOpenHelper {
    /*
     * Is the database Version
     */
    public static int DATABASE_VERSION = 1;
    /**
     * Is the database name
     */
    public static String DATABASE_NAME = "LifeMonitor";
    /**
     * Is the context application where this table is called
     */
    private Context context;

    /********/
    /* USER */
    /********/

    public static final String USER_TABLE_NAME = "Users";

    // users Table Columns names
    public static final String USER_KEY_ID = "user_id";
    public static final String USER_KEY_FIRSTNAME = "user_firstname";
    public static final String USER_KEY_SURNAME = "user_surname";
    public static final String USER_KEY_PH_NO = "user_phone_number";
    public static final String USER_KEY_EMAIL = "user_email";
    public static final String USER_KEY_BLOOD_GROUP = "user_blood_group";
    public static final String USER_KEY_URGENCY_NUMBER = "user_urgency_number";
    public static final String USER_KEY_DR_NAME = "user_dr_name";
    public static final String USER_KEY_DR_NUMBER = "user_dr_number";

    /***************/
    /* APPOINTMENT */
    /***************/

    /**
     * Is the appointment table's name
     */
    public static final String APPOINTMENT_TABLE_NAME = "Appointments";

    /**
     * Is the entry's id name
     */
    public static final String APPOINTMENT_FIELD_ID = "apptmt_id";
    /**
     * Is the foreign key which refers to user id from user table
     */
    public static final String APPOINTMENT_FIELD_USER = "apptmt_user_id";
    /**
     * Is the doctor id provide by REST service
     */
    public static final String APPOINTMENT_FIELD_DOCTOR = "apptmt_doctor_id";
    /**
     * Is the appointment's date formatted according to YYYY-MM-DDTHH:MM
     */
    public static final String APPOINTMENT_FIELD_DATE = "apptmt_date";

    public LocalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        this.thereCanBeOnlyOne();
        if (this.hasNoUsers()) {
            this.initEmpty();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // User table
        String CREATE_USERS_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_KEY_ID + " INTEGER PRIMARY KEY," + USER_KEY_FIRSTNAME + " TEXT,"
                + USER_KEY_SURNAME + " TEXT," + USER_KEY_PH_NO + " TEXT," + USER_KEY_EMAIL + " TEXT,"
                + USER_KEY_BLOOD_GROUP + " TEXT," + USER_KEY_URGENCY_NUMBER + " TEXT," + USER_KEY_DR_NAME + " TEXT,"
                + USER_KEY_DR_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);

        // Appointment table
        String parsedCommand = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER,  %s INTEGER, %s DATETIME, FOREIGN KEY (%s) REFERENCES %s(%s))";
        // table, id, user, doctor, date, foreign key
        String sqlQuery = String.format(
                parsedCommand,
                APPOINTMENT_TABLE_NAME,
                APPOINTMENT_FIELD_ID,
                APPOINTMENT_FIELD_USER,
                APPOINTMENT_FIELD_DOCTOR,
                APPOINTMENT_FIELD_DATE,
                APPOINTMENT_FIELD_USER,
                USER_TABLE_NAME,
                USER_KEY_ID
        );

        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME +", "+ APPOINTMENT_TABLE_NAME);
        onCreate(db);
    }

    /********/
    /* USER */
    /********/

    /**
     * Add a new user in the database
     * @param user user to add in the database
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_FIRSTNAME, user.getFirstName()); // user FName
        values.put(USER_KEY_SURNAME, user.getSurname()); // user FName
        values.put(USER_KEY_PH_NO, user.getPhoneNumber()); // user Phone
        values.put(USER_KEY_EMAIL, user.getEmail()); // user Email
        values.put(USER_KEY_BLOOD_GROUP, user.getEmail());
        values.put(USER_KEY_URGENCY_NUMBER, user.getEmail());
        values.put(USER_KEY_DR_NAME, user.getEmail());
        values.put(USER_KEY_DR_NUMBER, user.getEmail());

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Get the user identified by the id in parameter
     * @param id id of the user to find
     * @return the user in the local database
     */
    public User getUser(int id) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                USER_TABLE_NAME,
                new String[]{
                        USER_KEY_ID,
                        USER_KEY_FIRSTNAME,
                        USER_KEY_SURNAME,
                        USER_KEY_PH_NO,
                        USER_KEY_EMAIL,
                        USER_KEY_BLOOD_GROUP,
                        USER_KEY_URGENCY_NUMBER,
                        USER_KEY_DR_NAME,
                        USER_KEY_DR_NUMBER
                },
                USER_KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();

            User user = new User(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );

            cursor.close();
            db.close();

            return user;
        }
        else {
            db.close();
            throw new SQLException("Impossible to get the user with the id "+ id);
        }
    }

    /**
     * Get all users in the local database
     * @return list of users in the database
     */
    public List<User> getUsers() throws SQLException {
        List<User> list;

        try {
            list = new LinkedList<>();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + USER_TABLE_NAME;

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

                    list.add(user);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            return list;
        } catch (Exception e) {
            throw new SQLException("Impossible to retrieve all users from the database");
        }
    }

    /**
     * Update of the user
     * @param user user to update
     * @return the number of rows affected
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_FIRSTNAME, user.getFirstName());
        values.put(USER_KEY_SURNAME, user.getSurname());
        values.put(USER_KEY_PH_NO, user.getPhoneNumber());
        values.put(USER_KEY_EMAIL, user.getEmail());
        values.put(USER_KEY_BLOOD_GROUP, user.getBloodGroup());
        values.put(USER_KEY_URGENCY_NUMBER, user.getEmergencyNumber());
        values.put(USER_KEY_DR_NAME, user.getDrName());
        values.put(USER_KEY_DR_NUMBER, user.getDrNumber());

        return db.update(
                USER_TABLE_NAME,
                values,
                USER_KEY_ID + " = ?",
                new String[]{String.valueOf(user.getID())}
        );
    }

    /**
     * Delete the user in the database
     * @param id id of the user to delete
     */
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                USER_TABLE_NAME,
                USER_KEY_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    /**
     * Initialization of the user
     */
    private void initEmpty() {
        String lost = "";
        User user = new User(1, lost, lost, lost, lost, lost, lost, lost, lost);
        this.addUser(user);
    }


    /**
     * check if the database is empty
     * @return true if the user database is empty
     */
    public boolean hasNoUsers() {
        try {
            return this.getUsers().isEmpty();
        }
        catch (SQLException sqle) {
            return true;
        }
    }

    /**
     *   Get first user ID
     *   @return the first id in the user database
     */
    public int getFirstUserId() throws SQLException {
        if (this.hasNoUsers()) {
            throw new SQLException("There is no user to retrieve in the database");
        } else {
            return this.getUsers().get(0).getID();
        }

    }

    /**
     * there is only one user in the database
     */
    public void thereCanBeOnlyOne() {
        try {
            List<User> list = this.getUsers();
            int first = getFirstUserId();

            for (User u : list) {
                if (u.getID() != first) {
                    this.deleteUser(u.getID());
                }
            }
        }
        catch(SQLException sqle) { }
    }

    /*
    public void addAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String parsedCommand = "INSERT INTO %s (%s, %s, %s) VALUES (%d, %d, \"%s\")";
        String sqlQuery = String.format(
                parsedCommand,
                APPOINTMENT_TABLE_NAME,
                APPOINTMENT_FIELD_USER,
                APPOINTMENT_FIELD_DOCTOR,
                APPOINTMENT_FIELD_DATE,
                appointment.getUser().getId(),
                appointment.getDoctor().getId(),
                this.formatDate(appointment.getDate())
        );

        db.execSQL(sqlQuery);
        db.close();
    }


    public void updateAppointment(int appointmentID, Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String parsedCommand = "UPDATE %s SET %s=%s WHERE %s=%d";
        String sqlQuery = String.format(
                parsedCommand,
                APPOINTMENT_TABLE_NAME,
                APPOINTMENT_FIELD_DATE,
                this.formatDate(appointment.getDate()),
                APPOINTMENT_FIELD_ID, appointmentID
        );

        db.execSQL(sqlQuery);
        db.close();
    }

    /**
     * Remove an appointment from the table
     * @param appointmentID is the appointment's id which will be deleted
     /
    public void deleteAppointment(int appointmentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String parsedCommand = "DELETE FROM %s WHERE %s=%d";
        String sqlQuery = String.format(parsedCommand, APPOINTMENT_TABLE_NAME, APPOINTMENT_FIELD_ID, appointmentID);

        db.execSQL(sqlQuery);
        db.close();
    }

    /**
     * Gets an appointment from its id
     * @param appointmentID is the appointment's you want to extract from database
     * @return the appropriate appointment which has appointmentID value
     * @throws SQLException is raised when the date is malformed
     /
    public Appointment getAppointment(int appointmentID) throws SQLException {
        Patient patient;
        Doctor doctor;
        Calendar date;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query( // Request
                APPOINTMENT_TABLE_NAME,
                new String[] { APPOINTMENT_FIELD_USER, APPOINTMENT_FIELD_DOCTOR, APPOINTMENT_FIELD_DATE },
                APPOINTMENT_FIELD_ID + "=?",
                new String[] { String.valueOf(appointmentID) },
                null,
                null,
                null
        );

        Appointment appointment = new Appointment();

        try {
            if(cursor.moveToFirst()) {
                patient = this.getUserFromRemoteDataBase(Integer.parseInt(cursor.getString(cursor.getColumnIndex(APPOINTMENT_FIELD_USER))));
                doctor = this.getDoctorFromRemoteDataBase(Integer.parseInt(cursor.getString(cursor.getColumnIndex(APPOINTMENT_FIELD_DOCTOR))));
                date = parseDate(cursor.getString(cursor.getColumnIndex(APPOINTMENT_FIELD_DATE)));

                appointment.setUser(patient);
                appointment.setDoctor(doctor);
                appointment.setDate(date);
            }
            else {
                throw new SQLException("No appointment was found with the id "+ appointmentID);
            }
        }
        catch(SQLException sqle) {
            throw sqle;
        }
        catch(ParseException pe) {
            throw new SQLException("Impossible to parse appointment's date");
        }

        cursor.close();
        db.close();

        return appointment;
    }

    /**
     * Gets all appointments contains in this SQLite table
     * @return a list of all appointments
     * @throws SQLException is raised if one of retrieving appointment from remote database failed
     /
    public List<Appointment> getAllAppointment() throws SQLException {
        List<Appointment> appointmentList = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String parsedCommand = "SELECT * FROM %s";
        String sqlQuery = String.format(parsedCommand, APPOINTMENT_TABLE_NAME);

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if(cursor.moveToFirst()) {
            do {
                try {
                    appointmentList.add(this.getAppointment(Integer.parseInt(cursor.getString(cursor.getColumnIndex(APPOINTMENT_FIELD_USER)))));
                }
                catch(SQLException sqle) {
                    throw sqle;
                }
            } while(cursor.moveToNext());
        }

        return appointmentList;
    }

    /**
     * Converts a calendar instance into string type
     * @param calendar is the calendar object which will be converted
     * @return a string representation of calendar object formatted with YYYY-MM-DDTHH:MM
     /
    private String formatDate(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat(Appointment.DATE_FORMAT);

        return formatter.format(calendar.getTime());
    }

    /**
     * Converts a string representation of a date into a calendar instance
     * @param date is the string representation which will be converted
     * @return a a calendar object whose its string representation is formatted with YYYY-MM-DDTHH:MM.
     * @throws ParseException
     /
    private Calendar parseDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(Appointment.DATE_FORMAT);

        try {
            calendar.setTime(formatter.parse(date));
            return calendar;
        }
        catch(ParseException pe) {
            throw new ParseException("Impossible to parse date "+ date, 0);
        }
    }

    /**
     * Gets a user instance from the remote database using the RESTHelper class
     *
     * @see lifemonitor.application.helper.rest.RESTHelper
     *
     * @param userID is the user id you want to retrieve from the database
     * @return a user instance you are looking for
     * @throws SQLException is raised if the request to the remote database failed
     /
    private Patient getUserFromRemoteDataBase(int userID) throws SQLException {
        final String request = String.format("/patients/%d", userID);
        final Patient[] user = new Patient[1];

        new RESTHelper<Patient>(this.context).sendGETRequestForSingleResult(request, Patient.class, new SingleResultRESTListener<Patient>() {
            @Override
            public void onGetResponse(Patient result) {
                user[0] = result;
            }

            @Override
            public void onError() {}
        });

        if(user[0] != null) {
            return user[0];
        }
        else {
            throw new SQLException("The local appointement table seems to be empty");
        }
    }

    /**
     * Gets a doctor instance from the remote database using the RESTHelper class
     *
     * @see lifemonitor.application.helper.rest.RESTHelper
     *
     * @param doctorID is the doctor id you want to retrieve from the database
     * @return a doctor instance you are looking for
     * @throws SQLException is raised if the request to the remote database failed
     /
    private Doctor getDoctorFromRemoteDataBase(int doctorID) throws SQLException {
        final String request = String.format("/patients/%d", doctorID);
        final Doctor[] doctor = new Doctor[1];

        new RESTHelper<Doctor>(this.context).sendGETRequestForSingleResult(request, Doctor.class, new SingleResultRESTListener<Doctor>() {
            @Override
            public void onGetResponse(Doctor result) {
                doctor[0] = result;
            }

            @Override
            public void onError() {}
        });

        if(doctor[0] != null) {
            return doctor[0];
        }
        else {
            throw new SQLException("The local appointement table seems to be empty");
        }
    }
    */
}
