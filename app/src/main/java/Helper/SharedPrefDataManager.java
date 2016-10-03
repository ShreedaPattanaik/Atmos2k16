package Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 02-10-2016.
 */

public class SharedPrefDataManager {

    private static final String SHAR_PREF_NAME = "updated_times";
    private static final String TOKEN = "token";
    private static String SENT_TOKEN_TO_SERVER = "Sent_to_Server";

    public static void updateEventTime(Context context, Long time) {
        if (getEventTime(context) < time) {
            SharedPreferences.Editor editor = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE).edit();
            editor.putLong("event", time);
            editor.commit();
        }
    }

    public static long getEventTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getLong("event", 0);
    }

    public static void updateScheduleTime(Context context, long time) {
        if (getScheduleTime(context) < time) {
            SharedPreferences.Editor editor = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE).edit();
            editor.putLong("schedule", time);
            editor.commit();
        }
    }

    public static long getScheduleTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getLong("schedule", 0);
    }

    public static void updateToken(Context context, String refreshedToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SENT_TOKEN_TO_SERVER, false);
        editor.putString(TOKEN, refreshedToken);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }


    public static boolean tokenNeedToBeSent(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE);
        if (!preferences.getBoolean(SENT_TOKEN_TO_SERVER, false) && !getToken(context).isEmpty()) {
            return true;
        }
        return false;
    }

    public static void tokenSentToServer(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SENT_TOKEN_TO_SERVER, true);
        editor.commit();
    }
}
