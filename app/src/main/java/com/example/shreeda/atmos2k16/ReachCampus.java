package com.example.shreeda.atmos2k16;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReachCampus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReachCampus extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView reachus;
    View v;
    private int centerX=0;
    private int centerY=0;

    public ReachCampus() {
        // Required empty public constructor
    }

    public static ReachCampus newInstance(int centerX, int centerY) {
        ReachCampus fragment = new ReachCampus();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, centerX);
        args.putInt(ARG_PARAM2, centerY);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            centerX = getArguments().getInt(ARG_PARAM1);
            centerY = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.reach_campus, container, false);
        v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                if(centerY==0|| centerX==0){
                    return;
                }
                int width = v.getWidth();
                int height = v.getHeight();

                float finalRadius = Math.max(width, height) / 2 + Math.max(width - centerX, height - centerY);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Animator anim = ViewAnimationUtils.createCircularReveal(v, centerX, centerY, 0, finalRadius);
                    anim.setDuration(500);
                    anim.start();
                }

            }
        });
        return v;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private View circularReveal(View v) {
        int startRadius = 0;
        // get the final radius for the clipping circle
        int endRadius = Math.max(v.getWidth(), v.getHeight());

        Animator anim =
                ViewAnimationUtils.createCircularReveal(v, centerX, centerY, startRadius, endRadius);
        v.setVisibility(View.VISIBLE);
        anim.start();
        this.v = v;
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reachus = (TextView) view.findViewById(R.id.reach_campus);
        reachus.setText(Html.fromHtml("<h4>Campus Address</h4><p>BITS, Pilani - Hyderabad Campus<br>Jawahar Nagar,<br>Shameerpet Mandal,<br>Hyderabad, 500078, Telangana State.<br>INDIA</p><h4>Campus Location:</h4><p>BITS-Pilani, Hyderabad Campus is about 25 Kms from Secunderabad Central Railway station, on the  Rajiv Rahadari (Karimnagar Highway) near Hakimpet Air Force station. The campus is in a serene atmosphere amidst scenic terrain with small hillocks and urban forest, near Shameerpet lake. The campus spreads across 200 acres of land. The campus is situated in the vicinity of institutions like ICICI Knowledge Park, NALSAR Law University, SP Bio-Tech Park etc.</p><h4>How to reach BITS-Pilani, Hyderabad Campus from Secunderabad.</h4><p>From Secunderabad railway station to BITS-Pilani, Hyderabad Campus one has to travel 22 KM on Rajiv Rahadari/ Karim Nagar Highway, up to Dongala Mysamma Junction after Alankrita Resorts in Thumkunta Village and take a right turn towards Ghatkesar. After travelling 1.5 KMs on this road, there is again a diversion to the right leading to the Campus. BITS sign boards indicating the directions are available on the way.<br>There is a direct Bus No. 212 from Secunderabad Railway station (Gurudwara Point) to BITS-Pilani Hyderabad Campus main gate. Besides there are city buses having numbers 211S, 211A, 211C, 211D, 211E, 211J, 211K, 211T, 211U, 567, 568, 569 which ply from Secunderabad Railway Station to various destinations to the Campus side. Further, there are many APSRTC buses from Secunderabad, Jubilee bus stand to Karim Nagar, Siddhipet, Ramagundam, Gajwel, Cheriyal etc. People can board any of these buses, get down either at Thumkunta or Dongala Mysamma junction which is 1KM away and take an auto to the campus.</p><h4>APSRTC City Bus Timings :  Bus No.212</h4><p>From Secunderabad Railway Station (Gurudwara Point) to BITS Main gate:<br>07:50, 08:20, 08:50, 09:50, 11.00, 12.00, 13.00, 14:10, 15:05, 16:05, 17:20, 18:15, 19.15, 20.15, 21.15.<br>From BITS Main Gate to Secunderabad Railway Station :<br>06.55 , 07.55 , 08:50, 09:20, 09:50, 11.00, 12.00, 13.00, 14.00, 15:05, 16:05, 17:20, 18:15, 19.15, 20.15.<br>City buses having numbers 211S, 211A, 211C, 211D, 211E, 211J, 211K, 211T, 211U, 567, 568, 569 which ply from Secunderabad Railway Station to various destinations to the Campus side. Further, there are many APSRTC buses from Secunderabad, Jubilee bus stand to Karim Nagar, Siddhipet, Ramagundam, Gajwel, Cheriyal etc. People can board any of these buses, get down either at Thumkunta or Dongala Mysamma junction which is 1KM away and take an auto to the campus.</p>"));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.show_map_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=17.547622, 78.572282")));
            }
        });

    }


}
