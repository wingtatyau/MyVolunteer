package com.eeproject.myvolunteer.myvolunteer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeproject.myvolunteer.myvolunteer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChattActivity extends Fragment implements View.OnClickListener,
        MessageDataSource.MessagesCallbacks{

    public static final String USER_EXTRA = "USER";

    public static final String TAG = "ChatActivity";
    Context context;
    Button sendMessage;
    String newMessage;
    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private ListView mListView;
    private EditText newMessageView;
    private Date mLastMessageDate = new Date();
    private String mConvoId;
    private MessageDataSource.MessagesListener mListener;
    final String ACTIVITY_TAG="LogDemo";

    Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_chat);
//		getActivity().getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_mian, null);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        initView(v);

        return v;
    }

    public void initView(View v) {
        mRecipient = fragment_Chat.chat_receiver;
        toolbar.setTitle(mRecipient);
        mListView = (ListView)v.findViewById(R.id.messages_list);
        mListView.setAdapter(mAdapter);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);
        newMessageView = (EditText) v.findViewById(R.id.new_message);

        sendMessage = (Button)v.findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        String[] ids = {fragment_Chat.chat_receiver,"-", login.chat_sender};
        Arrays.sort(ids);
        mConvoId = ids[0]+ids[1]+ids[2];

        mListener = MessageDataSource.addMessagesListener(mConvoId, this);
    }

    public void onClick(View v)
    {
        newMessage = newMessageView.getText().toString();
        Log.v(ACTIVITY_TAG, newMessage + "dummy");
        newMessageView.setText("");
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(login.chat_sender);

        MessageDataSource.saveMessage(msg, mConvoId);
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }


    private class MessagesAdapter extends ArrayAdapter<Message> {
        MessagesAdapter(ArrayList<Message> messages){
            super(getActivity(), R.layout.message, R.id.message, messages);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.message);
            nameView.setText(message.getText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getSender().equals(fragment_Chat.chat_receiver)){
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getResources().getDrawable(R.drawable.bubble_left_gray));
                } else{
                    nameView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bubble_left_gray));
                }
                layoutParams.gravity = Gravity.LEFT;
            }else{
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getResources().getDrawable(R.drawable.bubble_right_green));
                } else{
                    nameView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bubble_right_green));
                }
                layoutParams.gravity = Gravity.RIGHT;
            }

            nameView.setLayoutParams(layoutParams);


            return convertView;
        }
    }
}
