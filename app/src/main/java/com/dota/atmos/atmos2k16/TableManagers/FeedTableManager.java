package com.dota.atmos.atmos2k16.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dota.atmos.atmos2k16.Set.FeedSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class FeedTableManager {
    public static final String KEY_ID = "ID";
    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_EVENT_NAME = "Name";
    public static final String KEY_RECEIVE_TIME = "ReceiveTime";
    public static final String KEY_MESSAGE = "Message";

    public static final String TAG = "FeedTable";

    private static final String DATABASE_TABLE = "FeedTable";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedDatabase";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public FeedTableManager(Context c) {
        context = c;
    }

    public FeedTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public long addEntry(JSONObject jsonObject) throws JSONException {
        long success;
        int id = 0;

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME, jsonObject.getString("title"));
        cv.put(KEY_EVENT_ID, jsonObject.getInt("id"));
        cv.put(KEY_RECEIVE_TIME, jsonObject.getLong("updated_at") * 1000);
        cv.put(KEY_MESSAGE, jsonObject.getString("message"));
        open();

        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            cv.remove(KEY_MESSAGE);
            success = ourDatabase.update(DATABASE_TABLE, cv, KEY_MESSAGE + "='" + jsonObject.getString("message") + "'", null);
        }
        close();
        return success;
    }

    public long addEntry(int event_id,
                         String name,
                         Long posted_time,
                         String message) {
        long success = -1;
        int id = 0;

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME, name);
        cv.put(KEY_EVENT_ID, event_id);
        cv.put(KEY_RECEIVE_TIME, posted_time);
        cv.put(KEY_MESSAGE, message);


        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            success = ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + "=" + id, null);
        }

        return success;
    }

    public ArrayList<FeedSet> getFeeds() {
        open();
        ArrayList<FeedSet> feeds = new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE + " order by " + KEY_RECEIVE_TIME + " desc ",
                null);
        if (cursor.moveToFirst())
            do {

                FeedSet feed = new FeedSet(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getLong(3)
                );
                feeds.add(feed);
            } while (cursor.moveToNext());
        cursor.close();
        close();
        return feeds;
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_EVENT_ID + " INTEGER NOT NULL, " +
                    KEY_EVENT_NAME + " TEXT NOT NULL, " +
                    KEY_RECEIVE_TIME + " TEXT NOT NULL, " +
                    KEY_MESSAGE + " TEXT UNIQUE NOT NULL);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}

