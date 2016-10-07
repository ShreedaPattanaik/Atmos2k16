package com.dota.atmos.atmos2k16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dota.atmos.atmos2k16.Adapter.FeedAdapter;
import com.dota.atmos.atmos2k16.Set.FeedSet;
import com.dota.atmos.atmos2k16.TableManagers.FeedTableManager;

import java.util.ArrayList;

import Helper.RecyclerClickListener;

/**
 * Created by SHREEDA on 06-10-2016.
 */

public class FeedFragment extends Fragment implements RecyclerClickListener {


    RecyclerView recyclerView;
    FeedAdapter mAdapter;
    ArrayList<FeedSet> feedSets;
    FeedTableManager tableManager;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.feed_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.feed_container);
        mAdapter = new FeedAdapter(getActivity());
        feedSets = new ArrayList<>();
        tableManager = new FeedTableManager(getActivity());
        feedSets = tableManager.getFeeds();
        mAdapter.setFeeds(feedSets);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setClickListener(this);
        if (feedSets.size() <= 0) {
            view.findViewById(R.id.feedEmpty).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.feedEmpty).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onClick(View v, int pos) {
        if (v.getId() == R.id.feed_row) {
            Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
            intent.putExtra("event_id", feedSets.get(pos).getEvent_id());
            startActivity(intent);
            Log.e("started", pos + " ");
            return true;
        }
        return false;
    }
}
