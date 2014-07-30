package com.oneroseapps.bartwakemeup.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;


public class WakeUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);







    }

    public void startNewTripClick(View v){
       // AlarmReceiver.alarmSound.stop();
        Intent startNewTrip = new Intent(getApplicationContext(), SetJourney.class);
        startActivity(startNewTrip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this removed top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wake_up, menu);
        return true;



    }

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
