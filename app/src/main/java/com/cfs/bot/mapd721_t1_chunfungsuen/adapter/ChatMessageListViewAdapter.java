package com.cfs.bot.mapd721_t1_chunfungsuen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cfs.bot.mapd721_t1_chunfungsuen.R;

import java.util.List;

public class ChatMessageListViewAdapter extends ArrayAdapter<String> {
    public ChatMessageListViewAdapter (
            Context context,
            int resource,
            List<String> objects
    ) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.chat_message_layout, parent, false);

        String message = getItem(position);
        TextView messageTextView = (TextView) itemView.findViewById(R.id.chat_message);
        messageTextView.setText(message);

        return itemView;
    }
}
