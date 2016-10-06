package com.example.shreeda.atmos2k16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by SHREEDA on 27-09-2016.
 */

public class SponsorsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponsors, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Picasso.with(getActivity()).load(R.drawable.spons12).fit().centerInside().into((ImageView) view.findViewById(R.id.title));
        Picasso.with(getActivity()).load(R.drawable.spons13).fit().centerInside().into((ImageView) view.findViewById(R.id.cotitle1));
        Picasso.with(getActivity()).load(R.drawable.spons14).fit().centerInside().into((ImageView) view.findViewById(R.id.cotitle2));
        Picasso.with(getActivity()).load(R.drawable.spons15).fit().centerInside().into((ImageView) view.findViewById(R.id.civilExpo));
        Picasso.with(getActivity()).load(R.drawable.sponsvel).fit().centerInside().into((ImageView) view.findViewById(R.id.gold));

    }
}
