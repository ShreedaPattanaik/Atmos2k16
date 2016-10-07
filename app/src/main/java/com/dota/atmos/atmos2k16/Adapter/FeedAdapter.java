package com.dota.atmos.atmos2k16.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dota.atmos.atmos2k16.R;
import com.dota.atmos.atmos2k16.Set.FeedSet;
import com.dota.atmos.atmos2k16.TableManagers.EventTableManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Helper.RecyclerClickListener;

/**
 * Created by SHREEDA on 06-10-2016.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;

    EventTableManager tableManager;

    ArrayList<FeedSet> feeds;
    RecyclerClickListener clickListener;

    public FeedAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        tableManager = new EventTableManager(context);

    }

    public void setClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFeeds(ArrayList<FeedSet> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.feed_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(feeds.get(position).getName());
        holder.post_time.setText(getTime(feeds.get(position).getPosted_time()));
        holder.message.setText(feeds.get(position).getMessage());
    }

    private String getTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a");
        String temp = format1.format(calendar.getTime());
        return temp;

    }


    @Override
    public int getItemCount() {
        return feeds.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name, post_time, message;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.feed_name);
            post_time = (TextView) itemView.findViewById(R.id.feed_time);
            message = (TextView) itemView.findViewById(R.id.feed_message);

            if (clickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                });
            }
        }
    }
}
