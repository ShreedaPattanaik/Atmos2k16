//package com.example.shreeda.atmos2k16;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.shreeda.atmos2k16.Services.EventTableManager;
//import com.example.shreeda.atmos2k16.Set.EventSet;
//
//import java.util.ArrayList;
//
//import Helper.RecyclerClickListener;
//
///**
// * Created by SHREEDA on 27-09-2016.
// */
//
//public class FavouritesFragment extends Fragment implements RecyclerClickListener {
//    RecyclerView recyclerView;
//    EventListingAdapter mAdapter;
//    EventTableManager tableManager;
//    ArrayList<EventSet> data;
//    public FavouriteFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_favourite, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_container);
//        mAdapter = new EventListingAdapter(getActivity(),getTag());
//        mAdapter.setClickListener(this);
//        tableManager = new EventTableManager(getActivity());
//
//        data = new ArrayList<>();
//        data = tableManager.getFavourites();
//        if(data.size()==0){
//            view.findViewById(R.id.favourite_empty).setVisibility(View.VISIBLE);
//        }
//        else{
//            view.findViewById(R.id.favourite_empty).setVisibility(View.GONE);
//        }
//        mAdapter.setEvents(data);
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }
//
//    @Override
//    public void onClick(View v, int pos) {
//        if (v.getId() == R.id.favourite_icon) {
//            tableManager.toggleFavourite(data.get(pos).getId());
//            data.remove(pos);
//            mAdapter.notifyItemRemoved(pos);
//        } else if (v.getId() == R.id.custom_event_row) {
//            Intent intent = new Intent(getActivity(), EventDataActivity.class);
//            intent.putExtra("Event_id", data.get(pos).getId());
//            startActivity(intent);
//        }
//    }
//}
