package com.example.facedemo.facedemo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eeproject.myvolunteer.myvolunteer.R;

public class MainActivity extends Activity {
	private ListView lv;
	private List<Map<String, Object>> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(getApplication());
			}
		}).start();
		lv = (ListView) findViewById(R.id.listlv);
		data = getData();
		final MyAdapter adapter = new MyAdapter(this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(adapter.getItem(position).toString().equals("zuyoChat")){
					Intent it=new Intent();
					it.setClass(MainActivity.this,ChatActivity.class);
					startActivity(it);
				}else{
					Toast.makeText(MainActivity.this,adapter.getItem(position)+"",Toast.LENGTH_LONG).show();
				}

			}
		});




		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(getApplication());
			}
		}).start();
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("title", "QuestList");
		list.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("title", "Add Quest");
		list.add(map1);
		list.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("title", "MyAccount");
		list.add(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("title", "zuyoChat");
		list.add(map4);
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("title", "Ranking");
		list.add(map5);
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("title", "share");
		list.add(map6);
		Map<String, Object> map7 = new HashMap<String, Object>();
		map7.put("title", "Setting");
		list.add(map7);
		Map<String, Object> map8 = new HashMap<String, Object>();
		map8.put("title", "zuyoChat");
		list.add(map8);
		Map<String, Object> map9 = new HashMap<String, Object>();
		map9.put("title", "zuyoChat");
		list.add(map9);
		return list;
	}

	//ViewHolder
	static class ViewHolder
	{
		public TextView title;
	}

	public class MyAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater = null;
		private MyAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			//How many items are in the data set represented by this Adapter.
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// Get the data item associated with the specified position in the data set.
			return data.get(position).get("title");
		}

		@Override
		public long getItemId(int position) {
			//Get the row id associated with the specified position in the list.
			return position;
		}

		//Get a View that displays the data at the specified position in the data set.
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if(convertView == null)
			{
				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.listlv, null);
				holder.title = (TextView)convertView.findViewById(R.id.name_tv);

				convertView.setTag(holder);
			}else
			{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.title.setText((String)data.get(position).get("title"));
			return convertView;
		}

	}
}
