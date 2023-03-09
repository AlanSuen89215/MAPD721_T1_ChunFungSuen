package com.cfs.bot.mapd721_t1_chunfungsuen.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.cfs.bot.mapd721_t1_chunfungsuen.R;
import com.cfs.bot.mapd721_t1_chunfungsuen.notification.NotificationDecorator;

public class ChatBotService extends Service {
    private static final String TAG = "ChatService";
    public static final String CMD = "msg_cmd";
    public static final int CMD_GENERATE_MESSAGE = 10;
    public static final int CMD_STOP_SERVICE = 20;
    public static final String KEY_USER_NAME = "user_name";
    private static final String last2DigitOfStudentNum = "69";

    private NotificationManager notificationMgr;
    private NotificationDecorator notificationDecorator;
    private PowerManager.WakeLock wakeLock;

    public ChatBotService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationDecorator = new NotificationDecorator(this, notificationMgr);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            Bundle data = intent.getExtras();
            handleData(data);
            if (!wakeLock.isHeld()) {
                Log.v(TAG, "acquiring wake lock");
                wakeLock.acquire();
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationMgr.cancelAll();
        Log.v(TAG, "releasing wake lock");
        wakeLock.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleData(Bundle data) {
        int command = data.getInt(CMD);
        if (command == CMD_GENERATE_MESSAGE) {
            String userName = (String) data.get(KEY_USER_NAME);
            // generate messages
            String firstMsg = String.format("Hello %s!", userName);
            String secondMsg = "How are you?";
            String thirdMsg = String.format("Good Bye %s!", userName);
            // send notifications of the messages
            notificationDecorator.displayExpandableNotification("Reply", firstMsg);
            notificationDecorator.displayExpandableNotification("Reply", secondMsg);
            notificationDecorator.displayExpandableNotification("Reply", thirdMsg);
            // send broadcasts of the messages
            sendBroadcastNewMessage(firstMsg);
            sendBroadcastNewMessage(secondMsg);
            sendBroadcastNewMessage(thirdMsg);
        }
        else if (command == CMD_STOP_SERVICE) {
            String message = String.format("ChatBot Stopped: %s", last2DigitOfStudentNum);
            notificationDecorator.displayExpandableNotification("Stop service", message);
            stopSelf();
        }
        else {
            Log.w(TAG, "Ignoring Unknown Command! id=" + command);
        }
    }

    private void sendBroadcastNewMessage(String message) {
        Intent intent = new Intent(
                getString(R.string.broadcast_intent_filter_chat_bot_reply)
        );
        Bundle data = new Bundle();
        data.putString(
                getString(R.string.chat_bot_reply_key_message),
                message
        );
        intent.putExtras(data);
        sendBroadcast(intent);
    }
}