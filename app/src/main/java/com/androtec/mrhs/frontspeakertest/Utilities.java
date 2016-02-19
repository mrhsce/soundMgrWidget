package com.androtec.mrhs.frontspeakertest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.PowerManager;

public class Utilities {
    private static AudioManager audioManager;
    private static PackageManager pm ;
    private static PowerManager powerManager;
    private static PowerManager.WakeLock wakeLock;
    private static int field = 0x00000020;

    public static void turnOnScreen(Context ctx){

        powerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);

        try {
            // Yeah, this is hidden field.
            field = PowerManager.class.getClass().getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);
        } catch (Throwable ignored) {
        }
        wakeLock = powerManager.newWakeLock(field, "bagi");

        if(wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    public static void turnOffScreen(Context ctx){

        powerManager = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        try {
            // Yeah, this is hidden field.
            field = PowerManager.class.getClass().getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);
        } catch (Throwable ignored) {
        }
        wakeLock = powerManager.newWakeLock(field, "bagi");

        if(!wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    public static void setEarpieceMode(Context ctx , Boolean flag)
    {

        pm = ctx.getPackageManager();
        audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);

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
    }

    public static  boolean haveTelephony()
    {
        return pm.hasSystemFeature("android.hardware.telephony");
    }
}
