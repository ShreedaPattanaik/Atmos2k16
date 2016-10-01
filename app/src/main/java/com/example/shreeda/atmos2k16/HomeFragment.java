package com.example.shreeda.atmos2k16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


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
        Picasso.with(getActivity()).load(R.drawable.landinglogo).fit().centerInside().into(logo);
        recyclerView = (RecyclerView) view.findViewById(R.id.headlinersRecycler);
        recyclerView.setAdapter(new HeadlinersAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
    }

    private class HeadlinersAdapter extends RecyclerView.Adapter<HeadlinersAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.event_card, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
