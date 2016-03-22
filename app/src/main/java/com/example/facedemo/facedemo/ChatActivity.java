package com.example.facedemo.facedemo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eeproject.myvolunteer.myvolunteer.R;

public class ChatActivity extends Fragment implements OnClickListener {

	private Button mBtnSend;

	private EditText mEditTextContent;

	private ListView mListView;

	private ChatMsgAdapter mAdapter;

	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_chat);
//		getActivity().getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 View v = inflater.inflate(R.layout.layout_chat, null);
		initView(v);
		initData();
		return v;
	}

	public void initView(View v) {
		mListView = (ListView) v.findViewById(R.id.listview);
		mBtnSend = (Button) v.findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) v.findViewById(R.id.et_sendmessage);
	}

	private String[] msgArray = new String[] {  };

	private String[] dataArray = new String[] {  };

	private final static int COUNT = 0;

	public void initData() {
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("hello");
				entity.setMsgType(true);
			} else {
				entity.setName("None");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgAdapter(getActivity(), mDataArrays);
		mListView.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		}
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& ((FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout))
//						.hideFaceView()) {
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setMsgType(false);
			entity.setText(contString);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}
}