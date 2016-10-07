package com.example.shreeda.atmos2k16.TableManagers;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shreeda.atmos2k16.EventListFragment;
import com.example.shreeda.atmos2k16.R;
import com.squareup.picasso.Picasso;

import Helper.RecyclerClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    RecyclerView recyclerView;
    FragmentManager manager;
    FragmentTransaction transaction;
    private String Tag="EventsFragment";

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.headlinersRecycler);
        recyclerView.setAdapter(new EventsFragment.EventsAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        manager=getActivity().getSupportFragmentManager();
    }
    private class EventsAdapter extends RecyclerView.Adapter<EventsFragment.EventsAdapter.MyViewHolder> {


        @Override
        public EventsFragment.EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventsFragment.EventsAdapter.MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.headliners_row, parent, false));
        }

        EventsFragment context;
        int[] resources;
        LayoutInflater inflater;

        public EventsAdapter(EventsFragment context) {
            this.context = context;
            inflater = LayoutInflater.from(getActivity());
            resources = new int[]{R.drawable.minigp, R.drawable.robowars, R.drawable.chemecar, R.drawable.mazeperilous, R.drawable.quadcopter, R.drawable.techexpo, R.drawable.pybits};

        }

        @Override
        public void onBindViewHolder(EventsFragment.EventsAdapter.MyViewHolder holder, int position) {
            Picasso.with(getActivity()).load(resources[position]).fit().centerCrop().into(holder.imageButton);
        }

        @Override
        public int getItemCount() {
            return resources.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageButton;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageButton = (ImageView) itemView.findViewById(R.id.main_events);
                if (clickListener != null) {
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickListener.onClick(v, getLayoutPosition());
                        }
                    });
                }
            }
        }

        RecyclerClickListener clickListener = new RecyclerClickListener() {
            @Override
            public void onClick(View v, int pos) {
                transaction = manager.beginTransaction();
                Fragment Events = EventListFragment.newInstance(pos);
                Log.e(Tag,"pos="+pos);
                transaction.replace(R.id.container, Events, "events");
                transaction.addToBackStack("events");
                transaction.commit();
            }
        };
    }
}
