package com.example.shreeda.atmos2k16.TableManagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shreeda.atmos2k16.Set.ScheduleSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class ScheduleTableManager {
    public static final String KEY_ID = "ID";
    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_EVENT_ROUND = "tag";
    public static final String KEY_EVENT_NAME = "Name";
    public static final String KEY_START_TIME = "StartTime";
    public static final String KEY_VENUE = "Venue";

    public static final String TAG = "ScheduleTable";

    private static final String DATABASE_TABLE = "ScheduleTable";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScheduleDatabase";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public ScheduleTableManager(Context c) {
        context = c;
    }

    public ScheduleTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public long addEntry(int event_id,
                         String event_round,
                         String name,
                         Long start_time,
                         String venue) {
        int id=0;
        long success = -1;

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME, name);
        cv.put(KEY_EVENT_ID, event_id);
        cv.put(KEY_EVENT_ROUND, event_round);
        cv.put(KEY_START_TIME, start_time);
        cv.put(KEY_VENUE, venue);


        try {
            success = ourDatabase.insertOrThrow(DATABASE_TABLE, null, cv);
        }catch (SQLiteConstraintException e){
            success=ourDatabase.update(DATABASE_TABLE,cv,KEY_ID+"="+id,null);
        }

        return success;
    }


    public ArrayList<ScheduleSet> getSchedule(long time) {
        open();
        ArrayList<ScheduleSet> sets=new ArrayList<>();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE "+KEY_START_TIME+"='"+time+"'", null);
        if(cursor.moveToFirst()){
            do{
                ScheduleSet set=new ScheduleSet(cursor.getString(3),cursor.getString(2),cursor.getString(5),cursor.getLong(4),cursor.getInt(1));
                sets.add(set);
            }while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return sets;
    }

    public ArrayList<Long> getDistinctTime(int day) {

        Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
        start.setTimeZone(TimeZone.getTimeZone("GMT"));
        end.setTimeZone(TimeZone.getTimeZone("GMT"));
        start.set(2016, Calendar.SEPTEMBER, 26 + day, 0, 0);
        end.set(2016, Calendar.SEPTEMBER, 27 + day, 0, 0);
        open();
        ArrayList<Long> times = new ArrayList<>();
        Cursor cursor = ourDatabase.rawQuery("SELECT DISTINCT " + KEY_START_TIME + " FROM " + DATABASE_TABLE +
                " WHERE CAST(" + KEY_START_TIME + " AS INTEGER) >= " + start.getTimeInMillis() +
                " AND CAST(" + KEY_START_TIME + " AS INTEGER) < " + end.getTimeInMillis() +
                " ORDER BY CAST(" + KEY_START_TIME + " AS INTEGER) ", null);
        if (cursor.moveToFirst()) {
            do {
                times.add(cursor.getLong(0));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        close();

        return times;
    }

    public Cursor getScheduleById(int id) {
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + KEY_EVENT_ID + "=" + id +
                " ORDER BY CAST(" + KEY_START_TIME + " AS INTEGER) ", null);
        close();
        return cursor;
    }

    public void deleteAllEntry() {
        open();
        ourDatabase.delete(DATABASE_TABLE, null, null);
        close();
    }


    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_EVENT_ID + " INTEGER NOT NULL, " +
                    KEY_EVENT_ROUND + " INTEGER NOT NULL, " +
                    KEY_EVENT_NAME + " TEXT NOT NULL, " +
                    KEY_START_TIME + " TEXT NOT NULL, " +
                    KEY_VENUE + " TEXT NOT NULL);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
