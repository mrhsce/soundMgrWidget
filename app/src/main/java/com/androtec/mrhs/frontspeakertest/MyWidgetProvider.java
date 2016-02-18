package com.androtec.mrhs.frontspeakertest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
    private static final String Action_sms = "ACTION_CLICK";
    private static final String Action_send = "ACTION_CLICK2";
    private static final String Action_next = "ACTION_CLICK3";
    private static final String Action_big = "ACTION_CLICK4";

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager , int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            String smsBody = "dsdd";
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.update, smsBody);

            // Register an onClickListener
            Intent intent = new Intent(context, getClass());
            intent.setAction(Action_next);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);

            Intent intent2 = new Intent(context, getClass());
            intent2.setAction(Action_sms);
            intent2.putExtra("sms", smsBody);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, 0);
            remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent2);

            Intent intent3 = new Intent(context, getClass());
            intent3.setAction(Action_send);
            intent3.putExtra("sms", smsBody);
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, 0, intent3, 0);
            remoteViews.setOnClickPendingIntent(R.id.imageView2, pendingIntent3);

            Intent intent4 = new Intent(context, getClass());
            intent4.setAction(Action_big);
            PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, 0, intent4, 0);
            remoteViews.setOnClickPendingIntent(R.id.imageView3, pendingIntent4);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (Action_sms.equals(intent.getAction())) {

        } else if (Action_next.equals(intent.getAction())) {
            String smsBody = "ddd";
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            watchWidget = new ComponentName(context, getClass());

            remoteViews.setTextViewText(R.id.update, smsBody);

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        } else if (Action_send.equals(intent.getAction())) {

        } else if (Action_big.equals(intent.getAction())) {
//            Intent intent2 = new Intent(context, splash.class);
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent2);
        }
    }
} 