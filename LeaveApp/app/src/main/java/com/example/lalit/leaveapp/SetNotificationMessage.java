package com.example.lalit.leaveapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SetNotificationMessage extends ActionBarActivity implements View.OnClickListener {
 TextView tvname,tvdepart,tvmob,tvhodtitle,engagetit,engageval,tvdeparttitle,dayti,dayco,tvleavetitile,leaveheading,leavecontent;
 String name,depart,mob,accept,reject,leave,day,engage,recognizer;
 Button btnfinal;
  public static int temp=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification_message);
         tvname=(TextView)findViewById(R.id.textview);
         tvdepart=(TextView)findViewById(R.id.textView2);
         tvmob=(TextView)findViewById(R.id.textView4);
         tvhodtitle=(TextView)findViewById(R.id.text);
         tvdeparttitle=(TextView)findViewById(R.id.textView);
         tvleavetitile=(TextView)findViewById(R.id.textView3);
         leaveheading=(TextView)findViewById(R.id.leaveheading);
         leavecontent=(TextView)findViewById(R.id.leavecontent);
         dayti=(TextView)findViewById(R.id.daytitle);
         dayco=(TextView)findViewById(R.id.daycontent);
        engagetit=(TextView)findViewById(R.id.engagetit);
        engageval =(TextView)findViewById(R.id.engageval);






        btnfinal=(Button)findViewById(R.id.btnfinal);
        btnfinal.setOnClickListener(this);

        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        name= bundle.getString("Message");
        depart=bundle.getString("Department");
        mob=bundle.getString("Mobile");
        accept=bundle.getString("Accept");
        reject=bundle.getString("Reject");
        leave=bundle.getString("Leave");
        day=bundle.getString("Day");
        engage=bundle.getString("Engage");
        recognizer=bundle.getString("Recognizer");

    if(recognizer.equals("Faculty")) {
        tvname.setText(name);
        tvdepart.setText(depart);
        tvmob.setText(mob);
        leavecontent.setText(leave);
        dayco.setText(day);
        engageval.setText(engage);
        temp++;
    }
        else{
        if(recognizer.equals("Hod")) {
            tvhodtitle.setText("Reply From HOD sir:-");
            if (!accept.isEmpty()) {
                tvname.setText(accept);

                tvdeparttitle.setText("");
                tvdepart.setText("");
                tvmob.setText("");
                tvleavetitile.setText("");
                leavecontent.setText("");
                leaveheading.setText("");
                dayti.setText("");
                dayco.setText("");
                engageval.setText("");
                engagetit.setText("");


            } /*else {
                tvname.setText(reject);
                tvdeparttitle.setText("");
                tvdepart.setText("");
                tvmob.setText("");
                tvleavetitile.setText("");
                leavecontent.setText("");
                leaveheading.setText("");
                dayti.setText("");
                dayco.setText("");
                engageval.setText("");
                engagetit.setText("");

            }*/
        }




    }

   }


    @Override
    public void onClick(View v) {


      Intent in=new Intent(this,FinalActivity.class);
          startActivity(in);

    }
}
