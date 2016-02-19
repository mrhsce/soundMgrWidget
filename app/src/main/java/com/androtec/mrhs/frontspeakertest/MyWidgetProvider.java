package com.androtec.mrhs.frontspeakertest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
    public static final String Action_ACTIVE = "ACTION_CLICK";
    public static final String Action_NOT_ACTIVE = "ACTION_CLICK2";
    public static final String Action_SEMI_STATE = "ACTION_CLICK3";
    public static final String SAVE_NAME_STATE = "STATESAVENAME";

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("bagi" , "update");
        ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String state = preferences.getString(MyWidgetProvider.SAVE_NAME_STATE, "");
            if (state.equals("")) {
                SharedPreferences.Editor editor = preferences.edit();
                Utilities.setEarpieceMode(context, false);
                editor.putString(MyWidgetProvider.SAVE_NAME_STATE, MyWidgetProvider.Action_NOT_ACTIVE);
                state = MyWidgetProvider.Action_NOT_ACTIVE;
                editor.commit();
            }

            if (state.equals(Action_ACTIVE)) {
                remoteViews.setImageViewResource(R.id.active, R.drawable.active_on);
                remoteViews.setImageViewResource(R.id.notActive, R.drawable.not_active_off);
                remoteViews.setImageViewResource(R.id.semiState, R.drawable.semi_off);

            } else if (state.equals(Action_NOT_ACTIVE)) {
                remoteViews.setImageViewResource(R.id.active, R.drawable.active_off);
                remoteViews.setImageViewResource(R.id.notActive, R.drawable.not_active_on);
                remoteViews.setImageViewResource(R.id.semiState, R.drawable.semi_off);

            } else if (state.equals(Action_SEMI_STATE)) {
                remoteViews.setImageViewResource(R.id.active, R.drawable.active_off);
                remoteViews.setImageViewResource(R.id.notActive, R.drawable.not_active_off);
                remoteViews.setImageViewResource(R.id.semiState, R.drawable.semi_on);
            }

            Intent intent2 = new Intent(context, getClass());
            intent2.setAction(Action_ACTIVE);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, 0);
            remoteViews.setOnClickPendingIntent(R.id.active, pendingIntent2);

            Intent intent3 = new Intent(context, getClass());
            intent3.setAction(Action_NOT_ACTIVE);
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, 0, intent3, 0);
            remoteViews.setOnClickPendingIntent(R.id.notActive, pendingIntent3);

            Intent intent4 = new Intent(context, getClass());
            intent4.setAction(Action_SEMI_STATE);
            PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, 0, intent4, 0);
            remoteViews.setOnClickPendingIntent(R.id.semiState, pendingIntent4);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String state = preferences.getString(MyWidgetProvider.SAVE_NAME_STATE, "");

        if (state.equals("")) {
            SharedPreferences.Editor editor = preferences.edit();
            Utilities.setEarpieceMode(context, false);
            editor.putString(MyWidgetProvider.SAVE_NAME_STATE, MyWidgetProvider.Action_NOT_ACTIVE);
            editor.commit();
        }

        if (Action_ACTIVE.equals(intent.getAction())) {
            context.stopService(new Intent(context, MyService.class));
            SharedPreferences.Editor editor = preferences.edit();
            Utilities.setEarpieceMode(context, true);
            editor.putString(MyWidgetProvider.SAVE_NAME_STATE, MyWidgetProvider.Action_ACTIVE);
            editor.commit();

        } else if (Action_NOT_ACTIVE.equals(intent.getAction())) {
            context.stopService(new Intent(context, MyService.class));
            SharedPreferences.Editor editor = preferences.edit();
            Utilities.setEarpieceMode(context, false);
            editor.putString(MyWidgetProvider.SAVE_NAME_STATE, MyWidgetProvider.Action_NOT_ACTIVE);
            editor.commit();

        } else if (Action_SEMI_STATE.equals(intent.getAction())) {
            context.startService(new Intent(context, MyService.class));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(MyWidgetProvider.SAVE_NAME_STATE, MyWidgetProvider.Action_SEMI_STATE);
            editor.commit();
        }
        if (!AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            Intent iii = new Intent(context, MyWidgetProvider.class);
            iii.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetProvider.class));
            iii.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(iii);
        }
    }
}