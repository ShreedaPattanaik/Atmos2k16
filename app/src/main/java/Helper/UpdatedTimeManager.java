package Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 02-10-2016.
 */

public class UpdatedTimeManager {

    private static final String SHAR_PREF_NAME = "updated_times";

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
}
