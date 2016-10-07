package com.dota.atmos.atmos2k16;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dota.atmos.atmos2k16.TableManagers.ScheduleTableManager;
import com.dota.atmos.atmos2k16.Set.ScheduleSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SHREEDA on 02-10-2016.
 */

public class TimelinePagerFragment extends Fragment {
    ArrayList<Long> times;


    public TimelinePagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    ScheduleTableManager mTableManager;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.container);
        TextView textView=(TextView)view.findViewById(R.id.notpresent);
        mTableManager = new ScheduleTableManager(getActivity());

        times = mTableManager.getDistinctTime(getArguments().getInt("day"));
        Log.e("TimelineFrag",times.toString());
        if(times.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }else{
            MyAdapter mAdapter=new MyAdapter(getActivity());
            mAdapter.setTimes(times);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            Log.e("Day "+getArguments().getInt("day"), String.valueOf(times.size()));
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View topLine, bottomLine;
        TextView time;
        //        RecyclerView recyclerView;
        LinearLayout linearLayout;
        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            topLine = itemView.findViewById(R.id.topLine);
            bottomLine = itemView.findViewById(R.id.bottomLine);
//            recyclerView= (RecyclerView) itemView.findViewById(R.id.container);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.timelineCard);
            this.itemView=itemView;
        }

        public void bindRecycler(Long time) {

            ArrayList<ScheduleSet> sets=mTableManager.getSchedule(time);
            linearLayout.removeAllViews();
            for(int i=0; i<sets.size(); i++) {
                View v =LayoutInflater.from(getActivity()).inflate( R.layout.custom_component_timeline_row, linearLayout,false);
                ((TextView)v.findViewById(R.id.event_name)).setText(sets.get(i).getName());
                ((TextView)v.findViewById(R.id.round_name)).setText(sets.get(i).getRound());
                ((TextView)v.findViewById(R.id.venue)).setText(sets.get(i).getVenue());
                linearLayout.addView(v);
                if(i!=sets.size()-1){
                    View divider=LayoutInflater.from(getActivity()).inflate( R.layout.divider, linearLayout,false);
                    linearLayout.addView(divider);
                }
            }
        }
    }




    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Context context;
        LayoutInflater inflater;
        ArrayList<Long> times;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            times = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_timeline_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.time.setText(getTime(times.get(position)) );
            if (position == times.size()-1) {
                holder.bottomLine.setVisibility(View.GONE);
            } else {
                holder.bottomLine.setVisibility(View.VISIBLE);
            }
            if (position == 0) {
                holder.topLine.setVisibility(View.GONE);
            } else {
                holder.topLine.setVisibility(View.VISIBLE);
            }
            holder.bindRecycler(times.get(position));

        }

        public void setTimes(ArrayList<Long> times) {
            this.times = times;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return times.size();
        }
    }

    private String getTime(long time) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(calendar.getTime());

    }


}


