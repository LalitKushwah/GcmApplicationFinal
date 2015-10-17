package com.example.lalit.leaveapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by lalit on 8/15/2015.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    String Department,message,Mobile,accept,reject,leave,days,engage,recognizer;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("Name");
        Department=data.getString("Department");
        Mobile=data.getString("Mobile");
        accept=data.getString("Accept");
        reject=data.getString("Reject");
        leave=data.getString("Leave");
        days=data.getString("Day");
        engage=data.getString("Engage");
        recognizer=data.getString("Recognizer");





        sendNotification(message);
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, SetNotificationMessage.class);
         Bundle extra=new Bundle();

         extra.putString("Message",message);
         extra.putString("Department",Department);
         extra.putString("Mobile",Mobile);
         extra.putString("Accept",accept);
         extra.putString("Reject",reject);
         extra.putString("Leave",leave);
         extra.putString("Day",days);
         extra.putString("Engage",engage);
         extra.putString("Recognizer",recognizer);
         intent.putExtras(extra);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.raw.logono)
                .setContentTitle("Acro Faculty Leave")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}