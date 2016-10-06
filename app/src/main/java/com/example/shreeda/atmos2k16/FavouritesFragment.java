package com.example.shreeda.atmos2k16;

import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;


import Helper.CursorRecyclerAdapter;

/**
 * Created by SHREEDA on 27-09-2016.
 */

public class FavouritesFragment extends Fragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Cursor cursor;
    MyAdapter myAdapter;
    public FavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView nofav;
    //    ImageView nofav;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventTableManager=new EventTableManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.favourite_container);
        nofav= (TextView) view.findViewById(R.id.no_favourite);

        cursor=null;//eventTableManager.getFavourites();todo change
        if (cursor.getCount()==0){
            recyclerView.setVisibility(View.GONE);
            nofav.setText("Oops! Looks like you haven't added any event to your favourites.");
//            nofav.setImageResource(R.drawable.favpic);
        }
        else {
            nofav.setVisibility(View.GONE);
            myAdapter = new MyAdapter(cursor, getContext());
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    EventTableManager eventTableManager;
    class MyViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView name;
        MaterialFavoriteButton fav;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            name=(TextView) itemView.findViewById(R.id.event_card_name);
            fav= (MaterialFavoriteButton) itemView.findViewById(R.id.favourite_button);
        }
    }

    class  MyAdapter extends CursorRecyclerAdapter<MyViewHolder> {

        Context context;
        LayoutInflater inflater;

        public MyAdapter(Cursor cursor, Context context) {
            super(cursor);
            this.context=context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.event_card,parent,false));
        }

        @Override
        public void onBindViewHolderCursor(final MyViewHolder holder, final Cursor cursor) {
            holder.name.setText(cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_NAME)));
//            cursor.close();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cursor.moveToPosition(holder.getAdapterPosition());
                    Intent intent=new Intent(getActivity(),EventDetailsActivity.class);
                    intent.putExtra("event_id",cursor.getInt(cursor.getColumnIndex(EventTableManager.KEY_EVENT_ID)));
                    startActivity(intent);                }
            });
            holder.fav.setFavorite(true,true);
            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cursor.moveToPosition(holder.getAdapterPosition());
                    eventTableManager.toggleFavourite(cursor.getInt(cursor.getColumnIndex(EventTableManager.KEY_EVENT_ID)));
                    cursor.requery();
                    myAdapter.notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
    }


}

