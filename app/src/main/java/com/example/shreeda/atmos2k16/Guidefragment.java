package com.example.shreeda.atmos2k16;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.example.shreeda.atmos2k16.Set.CampusMap;

import de.hdodenhof.circleimageview.CircleImageView;


// * Created by lakshmi sravani on 29-09-2016.
// *//*

public class Guidefragment extends Fragment {

    FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.guide_fragment, container, false);

        // ImageButton camBt = (ImageButton) v.findViewById(R.id.reach_campus);
        // camBt.setOnClickListener(listener);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = getActivity().getSupportFragmentManager();

        final CircleImageView[] circleImageView = new CircleImageView[3];
        circleImageView[0] = (CircleImageView) view.findViewById(R.id.circleImage1);
        circleImageView[1] = (CircleImageView) view.findViewById(R.id.circleImage2);
        circleImageView[2] = (CircleImageView) view.findViewById(R.id.circleImage3);


        CardView cd = (CardView) view.findViewById(R.id.card);
        cd.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment3 = new ReachCampus();
                transaction.replace(R.id.container, fragment3, "reach campus");
                transaction.addToBackStack("reach campus");
                transaction.commit();
            }
        });


        CardView cd1 = (CardView) view.findViewById(R.id.card1);
        cd1.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment4 = new CampusMap();
                transaction.replace(R.id.container, fragment4, "campus map");
                transaction.addToBackStack("campus map");
                transaction.commit();

            }
        });

        CardView cd2 = (CardView) view.findViewById(R.id.card2);
        cd2.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment4 = new CampusMap();
                transaction.replace(R.id.container, fragment4, "campus map");
                transaction.addToBackStack("campus map");
                transaction.commit();
            }
        });
        Animation slideRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);


        Animation slideLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);


        Animation slideRight2 = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right2);

        cd.startAnimation(slideRight);
        cd1.startAnimation(slideLeft);
        cd2.startAnimation(slideRight2);


        for (int i = 0; i < 3; i++) {
            Animation anim = new ScaleAnimation(
                    0f, 1f,
                    0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            anim.setDuration(200);
            anim.setStartOffset(300 + i * 100);
            anim.setFillAfter(true); // Needed to keep the result of the animation
            circleImageView[i].startAnimation(anim);
        }



     /*   TranslateAnimation slideInRight=new TranslateAnimation(-100,0,0,0);
        slideInRight.setDuration(300);
        cd.startAnimation(slideInRight);

        TranslateAnimation slideInLeft=new TranslateAnimation(100,0,0,0);
        slideInLeft.setDuration(300);
        slideInLeft.setStartOffset(200);
        cd1.startAnimation(slideInLeft);
        TranslateAnimation slideInRight2=new TranslateAnimation(-100,0,0,0);
        slideInRight2.setDuration(300);
        slideInRight2.setStartOffset(400);
        cd2.startAnimation(slideInRight2);*/


    }
/*

    class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("onSingleTap :", "" + e.getAction());
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            Log.i("onSingleTap :", "" + e.getAction());
            return true;
        }
    }*/


}



