package com.jorgesys.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnCustomNotification)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomNotification();
            }
        });

        (findViewById(R.id.btnBPSNotification)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBigPictureStyleNotification();
            }
        });


    }


    private void showCustomNotification(){
        int NOTIFICATION_ID = 1;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, getString(R.string.msg), when);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout);
        contentView.setImageViewResource(R.id.notification_image, R.drawable.pins_romania_mexico);
        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this, MainActivity2.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.contentIntent = contentIntent;

        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(NOTIFICATION_ID, notification);

        mNotificationManager.cancel(NOTIFICATION_ID);

    }


    private void showBigPictureStyleNotification(){

        int NOTIFICATION_ID = 2;
        String ns = Context.NOTIFICATION_SERVICE;

        //Get the bitmap to show in notification bar
        Bitmap bitmap_image = BitmapFactory.decodeResource(getResources(), R.drawable.pins_romania_mexico);

        Bitmap big_bitmap_image = BitmapFactory.decodeResource(getResources(), R.drawable.map_romania);


        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                .bigPicture(big_bitmap_image)
                .setSummaryText(getResources().getString(R.string.content));

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);

                nb.setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.content))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap_image)
                .setTicker("Hai Romania!")
                //API Level min 16
                .setStyle(style)
                .build();


        Intent notificationIntent = new Intent(this, MainActivity2.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        TaskStackBuilder TSB = TaskStackBuilder.create(this);
        TSB.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        TSB.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = TSB.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        nb.setContentIntent(resultPendingIntent);
        nb.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIFICATION_ID, nb.build());

    }

}
