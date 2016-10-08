package com.dota.atmos.atmos2k16;


import android.content.Intent;
import android.net.Uri;
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

    ImageView dev2, dev1, dev3, dev4;
    ImageView desg1, desg2, desg3, desg4;

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
        dev1 = (ImageView) view.findViewById(R.id.developer1Image);
        dev2 = (ImageView) view.findViewById(R.id.developer2Image);
        dev3 = (ImageView) view.findViewById(R.id.developer3Image);
        dev4 = (ImageView) view.findViewById(R.id.developer4Image);
        desg1 = (ImageView) view.findViewById(R.id.designer1Image);
        desg2 = (ImageView) view.findViewById(R.id.designer2Image);
        desg3 = (ImageView) view.findViewById(R.id.designer3Image);
        desg4 = (ImageView) view.findViewById(R.id.designer4Image);
        Picasso.with(getActivity()).load(R.drawable.shreeda).into(dev3);
        Picasso.with(getActivity()).load(R.drawable.rjain).into(dev2);
        Picasso.with(getActivity()).load(R.drawable.sravani).into(dev4);
        Picasso.with(getActivity()).load(R.drawable.harshit).into(dev1);
        Picasso.with(getActivity()).load(R.drawable.rajiv).into(desg1);
        Picasso.with(getActivity()).load(R.drawable.nithya).into(desg4);
        Picasso.with(getActivity()).load(R.drawable.gagan).into(desg2);
        Picasso.with(getActivity()).load(R.drawable.anshuman).into(desg3);
        view.findViewById(R.id.developer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/harshit-agarwal-32aba1101?authType=NAME_SEARCH&authToken=TjEm&locale=en_US&trk=tyah&trkInfo=clickedVertical%3Amynetwork%2CclickedEntityId%3A435914386%2CauthType%3ANAME_SEARCH%2Cidx%3A1-2-2%2CtarId%3A1475933853069%2Ctas%3Aharshit"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.developer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/rajat-jain-796772126?trk=nav_responsive_tab_profile_pic"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.developer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/shreeda.pattanaik?fref=ts"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.developer4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/jagini.sravs?ref=br_rs"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.design1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SaiRajivKrishna"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.design1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/gagan.aditya.5?fref=ts"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.design1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anshuman.das.9678?hc_ref=NEWSFEED"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.design1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nithyavardhan?fref=ts"));
                startActivity(browserIntent);
            }
        });

    }
}
