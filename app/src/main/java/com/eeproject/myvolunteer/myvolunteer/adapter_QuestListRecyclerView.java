package com.eeproject.myvolunteer.myvolunteer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Altman on 2016/3/23.
 */
public class adapter_QuestListRecyclerView extends RecyclerView.Adapter<adapter_QuestListRecyclerView.ViewHolder>{
    private List<quest> mQuest;



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, expirydate;
        ImageView questicon;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView_title);
            expirydate = (TextView) itemView.findViewById(R.id.textView_expirydate);
            questicon = (ImageView) itemView.findViewById(R.id.imageView_questicon);
        }
    }

    public adapter_QuestListRecyclerView(List<quest> mQuest){
        this.mQuest = mQuest;
    }

    @Override
    public adapter_QuestListRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(adapter_QuestListRecyclerView.ViewHolder holder, int position) {
        quest temp = mQuest.get(position);
        holder.title.setText(temp.getTitle());
        holder.expirydate.setText(temp.getExpirydate());
        int id = R.drawable.nomorewtf;
        holder.questicon.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return mQuest.size();
    }

}
