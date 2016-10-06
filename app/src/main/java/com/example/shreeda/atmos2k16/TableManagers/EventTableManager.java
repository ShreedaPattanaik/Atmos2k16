package com.example.shreeda.atmos2k16.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shreeda.atmos2k16.Set.EventSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helper.SharedPrefDataManager;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class EventTableManager {

    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_TAB = "EventTab";
    public static final String KEY_NAME = "Name";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_CONTACTS = "Contacts";
    public static final String KEY_IMAGE_LINK = "ImageLink";
    public static final String KEY_IMAGE_DOWNLOAD = "ImageDownloaded";
    public static final String KEY_FAVOURITE = "Favourite";
    public static final String KEY_PRIZE = "Prize";
    public static final String KEY_PROBST = "Problem_Statement";
    public static final String KEY_FBURL = "Fb_url";
    public static final String KEY_PDFLINK = "Pdf_link";


    public static final String TAG = "TransactionTable";

    private static final String DATABASE_TABLE = "EVENTLIST";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "EVENTDatabase";
    public Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public EventTableManager(Context c) {
        context = c;
    }

    public EventTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public ArrayList<String> getDistinctTabs(int type) {
        ArrayList<String> temp = new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT DISTINCT " + KEY_TAB + " FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_TYPE + "=" + type,
                null);
        if (cursor.moveToFirst())
            do {
                temp.add(cursor.getString(0));
            } while (cursor.moveToNext());
        cursor.close();
        close();
        return temp;
    }

    public ArrayList<EventSet> getEvents(String tab, int type) {
        ArrayList<EventSet> events = new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_TAB + " = '" + tab + "' and " + KEY_TYPE + "=" + type,
                null);
        if (cursor.moveToFirst()) {
            do {
                EventSet set = new EventSet(cursor, false);
                events.add(set);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return events;
    }

    public ArrayList<EventSet> getFavourites() {
        ArrayList<EventSet> events = new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_FAVOURITE + " = " + 1,
                null);
        if (cursor.moveToFirst()) {
            do {
                EventSet set = new EventSet(cursor, false);
                events.add(set);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return events;
    }

    /**
     * Notify table that image has been downloaded. And the new path generated is stored as image link
     *
     * @param id   event_id of the event
     * @param path The path of the image
     */
    public void imageDownloaded(int id, String path) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IMAGE_LINK, path);
        contentValues.put(KEY_IMAGE_DOWNLOAD, 1);
        open();
        ourDatabase.update(DATABASE_TABLE, contentValues, KEY_EVENT_ID + " = " + id, null);
        close();
    }

    public Boolean checkFavourite(int event_id) {
        Boolean result = false;
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_FAVOURITE + " FROM " + DATABASE_TABLE + " WHERE " + KEY_EVENT_ID + " = " + event_id + " ",
                null);
        if (cursor.moveToFirst()) {
            if (cursor.getInt(0) == 1)
                result = true;
        }
        close();
        cursor.close();
        return result;
    }

    /**
     * It toggles Favourite for the given event_id
     *
     * @param event_id
     * @return the current value of Favourite.
     */
    public boolean toggleFavourite(int event_id) {
        ContentValues cv = new ContentValues();
        boolean result;
        if (checkFavourite(event_id)) {
            cv.put(KEY_FAVOURITE, 0);
            result = false;
        } else {
            cv.put(KEY_FAVOURITE, 1);
            result = true;
        }
        open();
        ourDatabase.update(DATABASE_TABLE, cv, KEY_EVENT_ID + " = " + event_id, null);
        close();
        return result;
    }


    public long addEntry(JSONObject jsonObject) throws JSONException {

        ContentValues cv = new ContentValues();
        cv.put(KEY_EVENT_ID, jsonObject.getInt("event_id"));
        cv.put(KEY_PRIZE, jsonObject.getString("prize"));
        cv.put(KEY_PROBST, jsonObject.getString("problem_statement"));
        cv.put(KEY_DESCRIPTION, jsonObject.getString("description"));
        cv.put(KEY_IMAGE_LINK, jsonObject.getString("image_link"));
        cv.put(KEY_FBURL, jsonObject.getString("fb_url"));
        cv.put(KEY_TYPE, jsonObject.getInt("type"));
        cv.put(KEY_TAB, jsonObject.getString("tab"));
        cv.put(KEY_NAME, jsonObject.getString("event_name"));
        cv.put(KEY_CONTACTS,jsonObject.getString("contacts"));
        cv.put(KEY_IMAGE_DOWNLOAD, 0);
        cv.put(KEY_FAVOURITE, 0);
        long success = -1;
        open();
        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        } catch (SQLiteConstraintException e) {
            cv.remove(KEY_EVENT_ID);
            success = ourDatabase.update(DATABASE_TABLE, cv, KEY_EVENT_ID + "=" + jsonObject.getInt("event_id"), null);
        }
        close();
        return success;
    }

    public void deleteEntry(JSONObject jsonObject) throws JSONException {

        int id = jsonObject.getInt("event_id");
        open();
        ourDatabase.delete(DATABASE_TABLE, KEY_EVENT_ID + "=" + id, null);
        close();

    }

    public EventSet getEventData(int event_id) {
        EventSet eventSet = null;
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_EVENT_ID + " = " + event_id + " ",
                null);
        if (cursor.moveToFirst()) {
            eventSet = new EventSet(cursor,true);
        }
        close();
        cursor.close();
        return eventSet;
    }


    private static class DBHelper extends SQLiteOpenHelper {

        Context context;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TAB + " TEXT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_DESCRIPTION + " TEXT,  " +
                    KEY_FBURL + " TEXT,  " +
                    KEY_CONTACTS + " TEXT,  " +
                    KEY_PDFLINK + " TEXT,  " +
                    KEY_TYPE + " INTEGER NOT NULL,  " +
                    KEY_PRIZE + " TEXT, " +
                    KEY_PROBST + " TEXT,  " +
                    KEY_IMAGE_LINK + " TEXT,  " +
                    KEY_IMAGE_DOWNLOAD + " INTEGER,  " +
                    KEY_FAVOURITE + " INTEGER NOT NULL); ";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
            SharedPrefDataManager.overrideEventTime(context, 0L);
        }

    }
}
