package com.oneroseapps.bartwakemeup.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class TimeLeftActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_time_left);
        //this sets the time that the train is expected to arrive
        textView = (TextView) findViewById(R.id.timeLeftNumber);
        //this will set the text
        //textView.setText(arrivalTimeExpected);
        textView.setText( SetJourney.alarmTime);

    }

    public void CancelTrip(View v){
        //cancelling the alarm set
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(SetJourney.pendingIntent);
        Log.d("Debug","Cancelling the alarm");
        Intent cancel = new Intent(getApplicationContext(), SetJourney.class);
        startActivity(cancel);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this removed top bar

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_left, menu);
        return true;
    }*/





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
