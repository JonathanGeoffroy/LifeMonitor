package lifemonitor.application.controller.user_config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SqliteController extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public SqliteController(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 1);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE User ( UserId INTEGER PRIMARY KEY, UserFirstName TEXT, UserSurname TEXT, UserPhone TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT, "User Created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS Users";
        database.execSQL(query);
        onCreate(database);
    }

    public void insertUser(ArrayList<String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName", queryValues.get(0));
        database.insert("Users", null, values);
        database.close();
    }

    public int updateUser(ArrayList<String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName", queryValues.get(0));
        return database.update("Users", values, "UserId" + " = ?", new String[]{queryValues.get(0)});
    }

    public void deleteUser(String id) {
        Log.d(LOGCAT, "delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM Users where UserId='" + id + "'";
        Log.d("query", deleteQuery);
        database.execSQL(deleteQuery);
    }

    public ArrayList<ArrayList<String>> getAllUsers() {
        ArrayList<ArrayList<String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Users";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> map = new ArrayList<String>();
                map.add(cursor.getString(0));
                map.add(cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public ArrayList<String> getUserInfo(String id) {
        ArrayList<String> wordList = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM Users where UserId='" + id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                wordList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}

