package com.example.lalit.leaveapp;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by lalit on 8/15/2015.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";


    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, MyRegistrationIntentService.class);
        startService(intent);
    }
}