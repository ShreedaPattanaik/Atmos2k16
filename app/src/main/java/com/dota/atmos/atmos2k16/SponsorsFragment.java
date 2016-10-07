package com.dota.atmos.atmos2k16;

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


        Picasso.with(getActivity()).load(R.drawable.cyient).fit().centerInside().into((ImageView) view.findViewById(R.id.title));
        Picasso.with(getActivity()).load(R.drawable.wat).fit().centerInside().into((ImageView) view.findViewById(R.id.cotitle1));
        Picasso.with(getActivity()).load(R.drawable.prodigy).fit().centerInside().into((ImageView) view.findViewById(R.id.cotitle2));
        Picasso.with(getActivity()).load(R.drawable.sbi).fit().centerInside().into((ImageView) view.findViewById(R.id.association));

    }
}
