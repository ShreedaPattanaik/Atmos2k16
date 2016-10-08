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
 * A simple {@link Fragment} subclass.
 */
public class AppCredits extends Fragment {

    ImageView dev2,dev1,dev3,dev4;
    ImageView desg1,desg2,desg3,desg4;
    public AppCredits() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_credits, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dev1=(ImageView)view.findViewById(R.id.developer1Image);
        dev2=(ImageView)view.findViewById(R.id.developer2Image);
        dev3=(ImageView)view.findViewById(R.id.developer3Image);
        dev4=(ImageView)view.findViewById(R.id.developer4Image);
        desg1=(ImageView)view.findViewById(R.id.designer1Image);
        desg2=(ImageView)view.findViewById(R.id.designer2Image);
        desg3=(ImageView)view.findViewById(R.id.designer3Image);
        desg4=(ImageView)view.findViewById(R.id.designer4Image);
        Picasso.with(getActivity()).load(R.drawable.shreeda).into(dev3);
        Picasso.with(getActivity()).load(R.drawable.rjain).into(dev2);
        Picasso.with(getActivity()).load(R.drawable.sravani).into(dev4);
        Picasso.with(getActivity()).load(R.drawable.harshit).into(dev1);
        Picasso.with(getActivity()).load(R.drawable.rajiv).into(desg1);
        Picasso.with(getActivity()).load(R.drawable.nithya).into(desg4);
        Picasso.with(getActivity()).load(R.drawable.gagan).into(desg2);
        Picasso.with(getActivity()).load(R.drawable.anshuman).into(desg3);
    }
}
