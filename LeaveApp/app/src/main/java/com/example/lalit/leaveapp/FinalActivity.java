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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FinalActivity extends ActionBarActivity implements View.OnClickListener {
   EditText etuser,etpass;
   Button btnlogin;
    ProgressDialog pDialog;
    JsonParser jsonParser=new JsonParser();
    public static final String LOGIN_URL="http://kushjalwa.netau.net/login.php";
    public static final String TAG_SUCCESS="success";
    public static final String TAG_MESSAGE="message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

         etuser=(EditText)findViewById(R.id.etuser);
         etpass=(EditText)findViewById(R.id.etpass);
         btnlogin=(Button)findViewById(R.id.button);

        btnlogin.setOnClickListener(this);
  }


    @Override
    public void onClick(View v)
    {

       new LoginHod().execute() ;
    }

    public class LoginHod extends AsyncTask<String, String, String> {



        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FinalActivity.this);
            pDialog.setMessage("Attempting Login");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args)
        {
          try {
               List<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("username", etuser.getText().toString()));
               params.add(new BasicNameValuePair("password", etpass.getText().toString()));



               JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                Log.d("Login attempt", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Login Successful!", json.toString());

                    return "success";

                }
                else
                {
                    Log.d("Sendind Fail", "Fail");
                    return "fail";


                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;

        }
        protected void onPostExecute(String upmessage)
        {

            pDialog.dismiss();
             if(upmessage=="success")
             {
                 Intent i=new Intent(FinalActivity.this,AfterLoginHod.class);
                  startActivity(i);

             }
            else{
                 Toast.makeText(getApplicationContext(),"Invalid Credential",Toast.LENGTH_SHORT).show();
             }


        }


    }

}


