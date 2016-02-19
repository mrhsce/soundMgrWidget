package com.androtec.mrhs.frontspeakertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends Activity {
    Switch frontSpeakerSwitch;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frontSpeakerSwitch = (Switch) findViewById(R.id.frontSpeakerSwitch);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        updateSwitchState();

        Intent i = new Intent(this , MyService.class);
        startService(i);


        //audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);

        Log.d("in call", Integer.toString(AudioManager.MODE_IN_CALL));
        Log.d("MODE_IN_COMMUNICATION" ,Integer.toString(AudioManager.MODE_IN_COMMUNICATION ));
        Log.d("MODE_NORMAL" ,Integer.toString(AudioManager.MODE_NORMAL ));
        Log.d("MODE_INVALID" ,Integer.toString(AudioManager.MODE_INVALID ));
        Log.d("MODE_RINGTONE" ,Integer.toString(AudioManager.MODE_RINGTONE ));
        Log.d("MODE_CURRENT" ,Integer.toString(AudioManager.MODE_CURRENT ));
        Log.d("ROUTE_EARPIECE" ,Integer.toString(AudioManager.ROUTE_EARPIECE));
        Log.d("ROUTE_ALL", Integer.toString(AudioManager.ROUTE_ALL ));

        frontSpeakerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioManager.getMode() == 0)
                {
                    Utilities.setEarpieceMode(MainActivity.this ,true);
                }
                else
                {
                    Utilities.setEarpieceMode(MainActivity.this ,false);
                }
            }
        });
    }

    void updateSwitchState()
    {
        if(audioManager.getMode() == AudioManager.MODE_IN_CALL || audioManager.getMode() == AudioManager.MODE_IN_COMMUNICATION){
            frontSpeakerSwitch.setChecked(true);
        }
        else {
            frontSpeakerSwitch.setChecked(false);
        }
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