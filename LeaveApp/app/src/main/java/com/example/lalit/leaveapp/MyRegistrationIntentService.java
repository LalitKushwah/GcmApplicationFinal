package com.example.lalit.leaveapp;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyRegistrationIntentService extends IntentService {
    public static String token = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG = "RegIntentService";
    public static final String TOKEN_URL = "http://kushjalwa.netau.net/Token_Registration.php";
    public static final String NOTIFICATION_URL = "http://kushjalwa.netau.net/ceo.php";
    private static final String[] TOPICS = {"global"};
    public ProgressDialog pDialog;
    Context context;
    JsonParser jsonParser = new JsonParser();

    public MyRegistrationIntentService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // In the (unlikely) event that multiple refresh operations occur simultaneously,
            // ensure that they are processed sequentially.
            synchronized (TAG) {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // [START get_token]

                InstanceID instanceID = InstanceID.getInstance(this);
                token = instanceID.getToken("879100952974",
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.i(TAG, "GCM Registration Token: " + token);

                Intent dialogIntent = new Intent(this, MainActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                dialogIntent.putExtra("token",token);
                startActivity(dialogIntent);


                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                // [END register_for_gcm]
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
    }


    public class SendingToken extends AsyncTask<String, String, String> {

        int success;

       /* private ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        InputStream inputStream = null;
        String result = "";
*/
        protected void onPreExecute() {
           /* progressDialog.setMessage("Your progress dialog message...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    //MyAsyncTask.this.cancel(true);
                }
            });*/
        }



        @Override
        protected String doInBackground(String... args) {
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("token", token));


                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(TOKEN_URL, "POST", params);


                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                } else {
                    Log.d("Sendind Fail", "Fail");
                }
            } catch (Exception ex) {
            }
            // [END subscribe_topics]
            return null;
//}
        }

        @Override
        protected void onPostExecute(String s) {

         //   pDialog.dismiss();
        }
    }


}