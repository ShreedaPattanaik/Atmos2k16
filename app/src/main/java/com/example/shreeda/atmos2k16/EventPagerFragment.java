package com.example.shreeda.atmos2k16;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shreeda.atmos2k16.Set.EventSet;
import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;

import java.util.ArrayList;

import Helper.RecyclerClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPagerFragment extends Fragment {

    ArrayList<EventSet> eventData;
    EventTableManager eventTableManager;
    String Tag="EventPagerFragment";

    public EventPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_pager, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.container);
        eventTableManager = new EventTableManager(getActivity());
        eventData = eventTableManager.getEvents(getArguments().getString("tab"), 1);  //todo change
        final EventListingAdapter mAdapter = new EventListingAdapter(getActivity());
        mAdapter.setClickListener(new RecyclerClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if (v.getId() == R.id.favourite_icon) {
                    //execute fav
                    boolean fav=eventTableManager.toggleFavourite(eventData.get(pos).getId());
                    eventData.get(pos).setFav(fav);
                    //todo animation heart
                    mAdapter.notifyItemChanged(pos);

                } else {
                    //exec open event detail
                    Log.e(Tag,"eventData.get(pos).getId()");
                    Intent intent=new Intent(getActivity(),EventDetailsActivity.class);
                    intent.putExtra("event_id",eventData.get(pos).getId());
                    startActivity(intent);
                }
            }
        });
        mAdapter.setEvents(eventData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e("" + getArguments().getInt("tab"), String.valueOf(eventData.size()));
    }


}
