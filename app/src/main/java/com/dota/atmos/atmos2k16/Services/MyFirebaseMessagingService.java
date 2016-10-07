package com.dota.atmos.atmos2k16.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dota.atmos.atmos2k16.EventDetailsActivity;
import com.dota.atmos.atmos2k16.R;
import com.dota.atmos.atmos2k16.TableManagers.FeedTableManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 03-10-2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "FIREBASE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String message = remoteMessage.getData().get("data");
        Log.d(TAG, "Notification Message Body: " + message);
//        {"message":"Hello5","title":"Test5"}
        //todo test
        try {
            JSONObject jsonObject = new JSONObject(message);
            if (!jsonObject.has("type") || jsonObject.getInt("type") == 1) {
                sendEventNotif(jsonObject);
            }
        } catch (JSONException e) {
            Log.e("Notification",e.toString());
        }

    }

    private void sendEventNotif(JSONObject message) throws JSONException {
        FeedTableManager feedTableManager = new FeedTableManager(this);
        feedTableManager.addEntry(message);
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("event_id", message.getInt("id"));
        sendNotification(message, intent);
    }

    private void sendNotification(JSONObject object, Intent intent) throws JSONException {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(object.getString("title"))
                .setContentText(object.getString("message"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notifId = sharedPreferences.getInt("notif", 0);
        notificationManager.notify(object.getString("message"), notifId, notificationBuilder.build());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("notif", notifId + 1);
        editor.apply();
    }


}
