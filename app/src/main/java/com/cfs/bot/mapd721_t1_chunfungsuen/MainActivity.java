package com.cfs.bot.mapd721_t1_chunfungsuen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.cfs.bot.mapd721_t1_chunfungsuen.adapter.ChatMessageListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ChatBotBroadcastReceiver chatBotBroadcastReceiver;
    private ListView chatMessageListView;
    private List<String> chatMessageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DEBUG
        chatMessageList.add("Hello");

        chatMessageListView = (ListView) findViewById(R.id.chat_message_list_view);
        chatMessageListView.setAdapter(new ChatMessageListViewAdapter(
                this,
                android.R.layout.simple_list_item_1,
                chatMessageList
        ));
        chatBotBroadcastReceiver = new ChatBotBroadcastReceiver();
    }

    public static class ChatBotBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}