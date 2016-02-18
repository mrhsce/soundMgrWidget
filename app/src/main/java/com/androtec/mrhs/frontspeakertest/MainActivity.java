package com.androtec.mrhs.frontspeakertest;

import android.app.Activity;
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


public class MainActivity extends Activity implements SensorEventListener {

    AudioManager audioManager;
    Switch frontSpeakerSwitch;


    private SensorManager mSensorManager;
    private Sensor mSensor;

    private PackageManager pm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = this.getPackageManager();
        audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        frontSpeakerSwitch = (Switch) findViewById(R.id.frontSpeakerSwitch);

        updateSwitchState();


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        //audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);

        Log.d("in call" ,Integer.toString(AudioManager.MODE_IN_CALL ));
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
                    setEarpiece(true);
                }
                else
                {
                    setEarpiece(false);
                }
            }
        });
    }


    public boolean haveTelephony()
    {
        return pm.hasSystemFeature("android.hardware.telephony");
    }

    public void setEarpiece(boolean flag)
    {
        byte byte0 = 0 ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            byte0 = AudioManager.MODE_IN_COMMUNICATION;
        } else{
            byte0 = AudioManager.MODE_IN_CALL;
        }
        if (!haveTelephony()) {
            return;
        }
        audioManager.setSpeakerphoneOn(false);
        if (flag)
        {
            audioManager.setMode(byte0);
            audioManager.setSpeakerphoneOn(false);
        } else
        {
            audioManager.setMode(0);
            audioManager.setRouting(AudioManager.MODE_NORMAL, 2, -1);

        }
        updateSwitchState();

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


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        Toast.makeText(this, "sensor changed", Toast.LENGTH_LONG);
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