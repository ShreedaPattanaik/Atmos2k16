package com.dota.atmos.atmos2k16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
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
        Picasso.with(getActivity()).load(R.drawable.latestmonochrome2).fit().centerInside().into(logo);
        recyclerView = (RecyclerView) view.findViewById(R.id.headlinersRecycler);
        recyclerView.setAdapter(new HeadlinersAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
        logo.startAnimation(animation);

    }


    private class HeadlinersAdapter extends RecyclerView.Adapter<HeadlinersAdapter.MyViewHolder> {


        HomeFragment context;
        int[] resources;
        LayoutInflater inflater;
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
        int prev = -1;

        public HeadlinersAdapter(HomeFragment context) {
            this.context = context;
            inflater = LayoutInflater.from(getActivity());
            resources = new int[]{R.drawable.minigp, R.drawable.robowars, R.drawable.chemecar, R.drawable.mazeperilous, R.drawable.quadcopter, R.drawable.techexpo, R.drawable.pybits};

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.headliners_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Picasso.with(getActivity()).load(resources[position]).fit().centerCrop().into(holder.imageButton);
            if (prev < position) {
                prev = position;
            } else {
                return;
            }
            Animation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(200);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.setFillAfter(true);
            animation.setStartOffset(150+position * 150);
            holder.itemView.startAnimation(animation);

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
    }
}
