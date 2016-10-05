package com.example.shreeda.atmos2k16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import Helper.RecyclerClickListener;


public class HomeFragment extends Fragment {

    ImageView logo;
    RecyclerView recyclerView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logo = (ImageView) view.findViewById(R.id.logo);
        Picasso.with(getActivity()).load(R.drawable.atmos16).fit().centerInside().into(logo);
        recyclerView = (RecyclerView) view.findViewById(R.id.headlinersRecycler);
        recyclerView.setAdapter(new HeadlinersAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
    }


    private class HeadlinersAdapter extends RecyclerView.Adapter<HeadlinersAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.headliners_row, parent, false));
        }

        HomeFragment context;
        int[] resources;
        LayoutInflater inflater;

        public HeadlinersAdapter(HomeFragment context) {
            this.context = context;
            inflater = LayoutInflater.from(getActivity());
            resources = new int[]{R.drawable.minigp, R.drawable.robowars, R.drawable.chemecar, R.drawable.mazeperilous, R.drawable.quadcopter, R.drawable.techexpo, R.drawable.pybits};

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Picasso.with(getActivity()).load(resources[position]).fit().centerCrop().into(holder.imageButton);
//            holder.imageButton.setImageResource(resources[position]);

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
                switch (pos) {
                    case 0:
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:


                }
            }
        };
    }
}
