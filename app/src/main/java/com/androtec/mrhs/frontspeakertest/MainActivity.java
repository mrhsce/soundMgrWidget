package com.androtec.mrhs.frontspeakertest;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends Activity {

    Switch frontSpeakerSwitch;
    AudioManager audioManager;
    Boolean frontSpeakerActivated;
    public MediaPlayer m_mpSpeakerPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frontSpeakerSwitch = (Switch)findViewById(R.id.frontSpeakerSwitch);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        Log.d("Main",Integer.toString(audioManager.getMode()));
        if(audioManager.getMode() == AudioManager.MODE_IN_CALL){
            frontSpeakerActivated = true;
            frontSpeakerSwitch.setChecked(true);
        }
        else{
            frontSpeakerActivated = false;
            frontSpeakerSwitch.setChecked(false);
        }


        frontSpeakerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View compoundButton){
                frontSpeakerActivated = !frontSpeakerActivated;
                if (frontSpeakerActivated) {
                    frontSpeakerSwitch.setChecked(true);
//                    m_mpSpeakerPlayer.
                    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                    audioManager.setSpeakerphoneOn(false);
                    Log.d("onclick","front speaker turned on");
                    Log.d("Main",Integer.toString(audioManager.getMode()));
                    Toast.makeText(MainActivity.this,"set to earpiece",Toast.LENGTH_LONG).show();
//                    audioManager.setSpeakerphoneOn(false);
//
//                    audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
//
//
//                    setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
//
//
//                    audioManager.setMode(AudioManager.MODE_IN_CALL);
//
//                    m_mpSpeakerPlayer.reset();
//                    try {
//                        m_mpSpeakerPlayer.setDataSource("sdcard/90.mp3");
//                        m_mpSpeakerPlayer.prepare();
//                    }
//                    catch (io)
//
//                    m_mpSpeakerPlayer.start();
                } else {
                    frontSpeakerSwitch.setChecked(false);
                    audioManager.setMode(AudioManager.MODE_NORMAL);
                    audioManager.setSpeakerphoneOn(true);
                    Log.d("onclick","front speaker turned off");
                    Log.d("Main",Integer.toString(audioManager.getMode()));
                    Toast.makeText(MainActivity.this,"set to speaker",Toast.LENGTH_LONG).show();
                }
            }
        });


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
