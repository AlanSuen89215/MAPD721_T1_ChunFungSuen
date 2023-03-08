package com.cfs.bot.mapd721_t1_chunfungsuen.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.cfs.bot.mapd721_t1_chunfungsuen.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationDecorator {
    private static final String TAG = "NotificationDecorator";
    private final Context context;
    private final NotificationManager notificationMgr;

    public NotificationDecorator(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationMgr = notificationManager;
    }

    public void displayExpandableNotification(String title, String contentText) {
        contentText = contentText + "\n"
                + "Chat Bot\n"
                + (new SimpleDateFormat("MM/dd/yyyy hh:mm:ss")).format(new Date());

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(contentText)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setLights(Color.BLUE, 1000, 1000)
                    .build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationMgr.notify(0, notification);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
