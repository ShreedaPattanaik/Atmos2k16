package com.dota.atmos.atmos2k16;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dota.atmos.atmos2k16.Set.EventSet;
import com.dota.atmos.atmos2k16.TableManagers.EventTableManager;

import java.util.ArrayList;

import Helper.RecyclerClickListener;

/**
 * Created by lakshmi sravani on 28-09-2016.
 */
public class FavouriteFragment extends Fragment {
    ArrayList<EventSet> eventData;
    TextView textView;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.container);

        textView = (TextView) view.findViewById(R.id.notpresent);
        final EventTableManager eventTableManager = new EventTableManager(getActivity());


        eventData = eventTableManager.getFavourites();
        checkIfEmpty();

        final EventListingAdapter mAdapter = new EventListingAdapter(getActivity());
        mAdapter.setClickListener(new RecyclerClickListener() {
            @Override
            public void onClick(View v, int pos) {
                if (v.getId() == R.id.favourite_icon) {
                    //execute fav
                    eventTableManager.toggleFavourite(eventData.get(pos).getId());
                    eventData.remove(pos);
                    mAdapter.notifyItemRemoved(pos);
                    checkIfEmpty();

                } else {
                    //exec open event detail
                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                    intent.putExtra("event_id", eventData.get(pos).getId());
                    startActivity(intent);
                }
            }
        });

        mAdapter.setEvents(eventData);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void checkIfEmpty() {
        if (eventData.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            return;
        }
    }
}
