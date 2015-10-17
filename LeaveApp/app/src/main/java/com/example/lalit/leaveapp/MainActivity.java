package com.example.lalit.leaveapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    JsonParser jsonParser = new JsonParser();
    String str;
    private static final String TAG_SUCCESS = "success";
    public static final String NOTIFICATION_URL = "http://kushjalwa.netau.net/notification.php";
    public static final String TOKEN_URL = "http://kushjalwa.netau.net/Token_Registration.php";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public ProgressDialog progress;
    private TextView mInformationTextView;
    Button btn;
    EditText et;
    String msg;
    ProgressDialog pDialog;

  static boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (flag) {

            Intent intent = new Intent(this, MyRegistrationIntentService.class);
            startService(intent);
            flag=false;

        }
        else {
             Intent i=getIntent();
             str=i.getStringExtra("token");
              Log.d("Token in Activity",str);
            Log.d("CheckPoint", "check");
             new SendingNotification().execute();
            }
       }


 public class SendingNotification extends AsyncTask<String, String, String> {



        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("......Registering.......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args)
        {
          try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("token", str));
                Log.d("request!", "heloo");
                JSONObject json = jsonParser.makeHttpRequest(TOKEN_URL, "POST", params);
                Log.d("Login attempt", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Login Successful!", json.toString());
                }
                else
                {
                    Log.d("Sendind Fail", "Fail");
                }
            }
            catch (Exception ex)
            {
             ex.printStackTrace();
            }
            return null;
       }
        protected void onPostExecute(String file_url)
        {

             pDialog.dismiss();
             Intent i=new Intent(MainActivity.this,LoginPage.class);
             startActivity(i);
         }


        }
    }

