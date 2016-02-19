package com.androtec.mrhs.frontspeakertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String state = preferences.getString(MyWidgetProvider.SAVE_NAME_STATE, "");
        if (state.equals(""))
        {
            SharedPreferences.Editor editor = preferences.edit();
            Utilities.setEarpieceMode(this,false);
            editor.putString(MyWidgetProvider.SAVE_NAME_STATE, MyWidgetProvider.Action_NOT_ACTIVE);
            editor.commit();
        }


        //audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);

        Log.d("in call", Integer.toString(AudioManager.MODE_IN_CALL));
        Log.d("MODE_IN_COMMUNICATION" ,Integer.toString(AudioManager.MODE_IN_COMMUNICATION ));
        Log.d("MODE_NORMAL" ,Integer.toString(AudioManager.MODE_NORMAL ));
        Log.d("MODE_INVALID" ,Integer.toString(AudioManager.MODE_INVALID ));
        Log.d("MODE_RINGTONE" ,Integer.toString(AudioManager.MODE_RINGTONE ));
        Log.d("MODE_CURRENT" ,Integer.toString(AudioManager.MODE_CURRENT ));
        Log.d("ROUTE_EARPIECE" ,Integer.toString(AudioManager.ROUTE_EARPIECE));
        Log.d("ROUTE_ALL", Integer.toString(AudioManager.ROUTE_ALL ));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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