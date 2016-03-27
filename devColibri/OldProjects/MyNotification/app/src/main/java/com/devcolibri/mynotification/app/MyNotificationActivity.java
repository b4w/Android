package com.devcolibri.mynotification.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MyNotificationActivity extends Activity {

    private NotificationManager nm;
    private final int NOTIFICATION_ID = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showNotification(View view) {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher))
                .setTicker("Новое уведомление")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Уведомление")
                .setContentText("Нажмите что бы узнать секрет")
                .setProgress(100, 20, true);

        Notification notification = builder.build();

        notification.defaults = Notification.DEFAULT_VIBRATE;
        notification.sound = Uri.parse("android.resource://com.devcolibri.mynotification.app" + R.raw.believe_notification);

        long[] vibrate = new long[]{1500, 1000, 1500, 1000};
        notification.vibrate = vibrate;

        notification.flags = notification.flags | Notification.FLAG_INSISTENT;

        nm.notify(NOTIFICATION_ID, notification);
    }

    public void cancelNotification(View view) {
        nm.cancel(NOTIFICATION_ID);
    }
}
