package com.example.lalit.leaveapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AfterLoginHod extends ActionBarActivity implements View.OnClickListener {

  EditText etapprove,etreject;
    TextView recognizer1;
  Button btnapp,btnrj;
  ProgressDialog pDialog;
  public static final String NOTIFICATION_URL = "http://kushjalwa.netau.net/ceo.php";
  public static final String TAG_SUCCESS="success";
  JsonParser jsonParser=new JsonParser();
    String name,depart,mob,leave,day,engage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_hod);
        etapprove=(EditText)findViewById(R.id.etapp);
        etreject=(EditText)findViewById(R.id.etrj);
        recognizer1=(TextView)findViewById(R.id.recoghodlabel);
        btnapp=(Button)findViewById(R.id.button2);
        btnrj=(Button)findViewById(R.id.button3);

       btnapp.setOnClickListener(this);
       btnrj.setOnClickListener(this);

        Intent i=getIntent();
        name=i.getStringExtra("key");
        depart=i.getStringExtra("Department");
        mob=i.getStringExtra("Mobile");
        leave=i.getStringExtra("Leave");
        engage=i.getStringExtra("Engage");
        day=i.getStringExtra("Day");



    }


    @Override
    public void onClick(View v) {
         switch(v.getId())
         {
             case R.id.button2:
                 new HodApp().execute();
                 break;
             case R.id.button3:
                 new HodRej().execute();
                 break;


         }


    }

    public class HodApp extends AsyncTask<String, String, String> {


        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AfterLoginHod.this);
            pDialog.setMessage("....Sending Reply.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();


                params.add(new BasicNameValuePair("accept", etapprove.getText().toString()));
                params.add(new BasicNameValuePair("key",name));
                params.add(new BasicNameValuePair("Engage", engage));
                params.add(new BasicNameValuePair("Day",day));
                params.add(new BasicNameValuePair("Leave",leave));
                params.add(new BasicNameValuePair("Department",depart));
                params.add(new BasicNameValuePair("Recognizer",recognizer1.getText().toString()));



                Log.d("Reject",etreject.getText().toString());

              JSONObject json = jsonParser.makeHttpRequest(NOTIFICATION_URL, "POST", params);



                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                } else {
                    Log.d("Sendind Fail", "Fail");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

        }

     }

    public class HodRej extends AsyncTask<String, String, String> {


        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AfterLoginHod.this);
            pDialog.setMessage("....Sending Reply.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();


                params.add(new BasicNameValuePair("reject", etreject.getText().toString()));
                params.add(new BasicNameValuePair("key",name));
                params.add(new BasicNameValuePair("Engage", engage));
                params.add(new BasicNameValuePair("Day",day));
                params.add(new BasicNameValuePair("Leave",leave));
                params.add(new BasicNameValuePair("Department",depart));

                Log.d("Reject",etreject.getText().toString());

                JSONObject json = jsonParser.makeHttpRequest(NOTIFICATION_URL, "POST", params);



                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                } else {
                    Log.d("Sendind Fail", "Fail");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

        }



    }

    }
