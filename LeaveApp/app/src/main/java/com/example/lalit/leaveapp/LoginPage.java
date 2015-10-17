package com.example.lalit.leaveapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginPage extends ActionBarActivity implements View.OnClickListener{
      Button btn;
      EditText et,etdepartment,etmob,etdays;
      TextView recogLabel;
      Spinner sp;
      JsonParser jsonParser=new JsonParser();
      public static final String NOTIFICATION_URL = "http://kushjalwa.netau.net/notification.php";
      public static final String TAG_SUCCESS="success";
      ProgressDialog pDialog;
    Spinner dropdown,engage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn=(Button)findViewById(R.id.button);
        et=(EditText)findViewById(R.id.editText);
        etdepartment=(EditText)findViewById(R.id.etdepart);
        etmob=(EditText)findViewById(R.id.etmob);
        etdays=(EditText)findViewById(R.id.days);
        recogLabel=(TextView)findViewById(R.id.Recoglabel);
        btn.setOnClickListener(this);

       // sp=(Spinner)findViewById(R.id.spinner);
        dropdown = (Spinner)findViewById(R.id.spinner);
        engage = (Spinner)findViewById(R.id.spinner2);

        String[] items = new String[]{"Casual Leave", "Medical Leave", "EL","Study Leave"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        String[] item = new String[]{"Sushma Khatri", "Amit Khare", "Rahul Patidar","Narayan choudhary","Mahendra Yadav"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        engage.setAdapter(adapter1);






    }

 @Override

   public void onClick(View v)
    {
         new SendingMessage().execute();
    }
    public class SendingMessage extends AsyncTask<String, String, String>
    {
       int success;
          protected void onPreExecute()
          {
            pDialog = new ProgressDialog(LoginPage.this);
            pDialog.setMessage("......Sending-Notification.......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

         }

  @Override
        protected String doInBackground(String... args) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("key", et.getText().toString()));
                params.add(new BasicNameValuePair("Department", etdepartment.getText().toString()));
                params.add(new BasicNameValuePair("Mobile", etmob.getText().toString()));
                params.add(new BasicNameValuePair("Leave",dropdown.getSelectedItem().toString()));
                params.add(new BasicNameValuePair("Day",etdays.getText().toString()));
                params.add(new BasicNameValuePair("Engage",engage.getSelectedItem().toString()));
                params.add(new BasicNameValuePair("Recognizer",recogLabel.getText().toString()));


                Intent i=new Intent(LoginPage.this,AfterLoginHod.class);
                i.putExtra("key",et.getText().toString());
                i.putExtra("Department", etdepartment.getText().toString());
                i.putExtra("Mobile", etmob.getText().toString());
                i.putExtra("Leave", dropdown.getSelectedItem().toString());
                i.putExtra("Day", etdays.getText().toString());
                i.putExtra("Engage", engage.getSelectedItem().toString());


                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(NOTIFICATION_URL, "POST", params);
                Log.d("Login attempt", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                }
                else {
                    Log.d("Sendind Fail", "Fail");
                    }
             }
            catch (Exception ex) {
                     ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

             pDialog.dismiss();
        }
    }







}
