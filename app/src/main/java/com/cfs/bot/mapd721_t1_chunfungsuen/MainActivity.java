package com.cfs.bot.mapd721_t1_chunfungsuen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cfs.bot.mapd721_t1_chunfungsuen.adapter.ChatMessageListViewAdapter;
import com.cfs.bot.mapd721_t1_chunfungsuen.service.ChatBotService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ChatBotBroadcastReceiver chatBotBroadcastReceiver;
    private ListView chatMessageListView;
    private List<String> chatMessageList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        chatBotBroadcastReceiver = new ChatBotBroadcastReceiver(onReceiveChatBotMessageBroadcastListener);

        ((Button) findViewById(R.id.btn_generate_msg))
                .setOnClickListener(btnGenerateMessageOnClickListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(chatBotBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(
                getResources()
                        .getString(R.string.broadcast_intent_filter_chat_bot_reply)
        );
        registerReceiver(chatBotBroadcastReceiver, intentFilter);
    }

    private OnReceiveChatBotMessageBroadcastListener onReceiveChatBotMessageBroadcastListener = new OnReceiveChatBotMessageBroadcastListener() {

        @Override
        public void onReceive(String message) {
            chatMessageList.add(message);
            updateChatMessageListView();
        }
    };

    private void updateChatMessageListView() {
        chatMessageListView = (ListView) findViewById(R.id.chat_message_list_view);
        chatMessageListView.setAdapter(new ChatMessageListViewAdapter(
                this,
                android.R.layout.simple_list_item_1,
                chatMessageList
        ));
    }

    private View.OnClickListener btnGenerateMessageOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String userName = ((TextView) findViewById(R.id.user))
                    .getText().toString();

            if (userName.trim().equals("")) {
                Toast.makeText(context, "user name is empty", Toast.LENGTH_SHORT)
                        .show();
            }

            Bundle data = new Bundle();
            data.putInt(ChatBotService.CMD, ChatBotService.CMD_GENERATE_MESSAGE);
            data.putString(ChatBotService.KEY_USER_NAME, userName);
            Intent intent = new Intent(context, ChatBotService.class);
            intent.putExtras(data);
            startService(intent);
        }
    };

    public interface OnReceiveChatBotMessageBroadcastListener {
        void onReceive(String message);
    }

    public static class ChatBotBroadcastReceiver extends BroadcastReceiver {
        private OnReceiveChatBotMessageBroadcastListener listener;

        public ChatBotBroadcastReceiver(OnReceiveChatBotMessageBroadcastListener listener) {
            this.listener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            String message = data.getString(
                    context
                            .getString(R.string.chat_bot_reply_key_message)
            );
            listener.onReceive(message);
        }
    }
}