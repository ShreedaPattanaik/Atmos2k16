package com.example.shreeda.atmos2k16.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by admin on 03-10-2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "FIREBASE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
//        {"message":"Hello5","title":"Test5"}
        sendNotification(remoteMessage.getData().get("data"));
    }

    private void sendNotification(String data) {

    }
}
