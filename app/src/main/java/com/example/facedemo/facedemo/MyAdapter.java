package com.example.facedemo.facedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeproject.myvolunteer.myvolunteer.R;


class MyAdapter extends BaseAdapter
{
    private String[] str={"Apple","Amy","Peter","Alan","Hui"};
    private LayoutInflater mInflater = null;
    public MyAdapter(Context context)
    {
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // How many items are in the data set represented by this Adapter.
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        return str[position];
    }

    @Override
    public long getItemId(int position) {
        // Get the row id associated with the specified position in the list.
        return position;
    }


    static class ViewHolder
    {
        public ImageView img;
        public TextView name_tv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listlv, null);
            holder.img = (ImageView)convertView.findViewById(R.id.img_iv);
            holder.name_tv = (TextView)convertView.findViewById(R.id.name_tv);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
            holder.img.setImageResource(R.drawable.touxiang);
            holder.name_tv.setText(str[position]);
        }
        return convertView;
    }

}