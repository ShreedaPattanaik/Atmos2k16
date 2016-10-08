package com.dota.atmos.atmos2k16;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dota.atmos.atmos2k16.Set.EventSet;
import com.dota.atmos.atmos2k16.TableManagers.EventTableManager;
import com.dota.atmos.atmos2k16.TableManagers.EventsFragment;

import java.util.ArrayList;

import Helper.RecyclerClickListener;
import Helper.Share;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPagerFragment extends Fragment {

    private static final String ARG_PARAM1 = "arg1";
    ArrayList<EventSet> eventData;
    EventTableManager eventTableManager;
    String Tag = "EventPagerFragment";
    int mType;


    public EventPagerFragment() {
        // Required empty public constructor
    }

    public static EventPagerFragment newInstance(int type) {
        EventPagerFragment fragment = new EventPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(ARG_PARAM1);
        }
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
        eventData = eventTableManager.getEvents(getArguments().getString("tab"), mType);

        final EventListingAdapter mAdapter = new EventListingAdapter(getActivity());
        mAdapter.setDefaultImage(R.drawable.default_card_image);
        mAdapter.setClickListener(new RecyclerClickListener() {
            @Override
            public boolean onClick(View v, int pos) {
                if (v.getId() == R.id.favourite_icon) {
                    //execute fav
                    boolean fav = eventTableManager.toggleFavourite(eventData.get(pos).getId());
                    eventData.get(pos).setFav(fav);

                } else if (v.getId() == R.id.share_icon) {
                    Share.shareData(eventData.get(pos), getActivity());
                }
                return true;
            }
        });
        mAdapter.setEvents(eventData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
