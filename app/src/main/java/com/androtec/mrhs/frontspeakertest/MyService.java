package com.androtec.mrhs.frontspeakertest;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyService extends Service implements SensorEventListener {
    Sensor proxSensor;
    SensorManager sm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("bagi", "Service Created");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] < event.sensor.getMaximumRange()) {
            Utilities.setEarpieceMode(this, true);
            Utilities.turnOffScreen(this);
            Toast.makeText(this, "get near to face", Toast.LENGTH_LONG).show();
        }
        else {
            Utilities.setEarpieceMode(this, false);
            Utilities.turnOnScreen(this);
            Toast.makeText(this, "out of face", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        Log.d("bagi", "Service Stopped");
        sm.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("bagi", "Service Started");
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);

        return Service.START_STICKY;
    }
}