package com.oneroseapps.bartwakemeup.app;

/**
 * Created by valeriechao on 7/21/14.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dharm on 7/21/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent wakeUpIntent = new Intent(context,WakeUpActivity.class);
        context.startActivity(wakeUpIntent);



        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        // Get instance of Vibrator from current Context
        final Vibrator v2 = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //vibrate
        v2.cancel();
        v2.vibrate(500);
        //final long[] pattern = { 0, 100, 100, 100, 100, 100, 100, 100, 100 };
       // v2.vibrate(pattern, 0);
        Log.d("Debug", "Reached alarm now");
        //to play an alarm sound
        //initialize Caxirola sound
        final MediaPlayer alarmSound = MediaPlayer.create(context,R.raw.cax);
        alarmSound.start();


    }

}

