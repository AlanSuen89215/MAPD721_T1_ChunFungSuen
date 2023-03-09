package com.cfs.bot.mapd721_t1_chunfungsuen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

        // DEBUG
        chatMessageList.add("Hello");

        chatMessageListView = (ListView) findViewById(R.id.chat_message_list_view);
        chatMessageListView.setAdapter(new ChatMessageListViewAdapter(
                this,
                android.R.layout.simple_list_item_1,
                chatMessageList
        ));
        chatBotBroadcastReceiver = new ChatBotBroadcastReceiver();

        ((Button) findViewById(R.id.btn_generate_msg))
                .setOnClickListener(btnGenerateMessageOnClickListener);
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

    public static class ChatBotBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}