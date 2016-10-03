/*
package com.example.shreeda.atmos2k16;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;
import android.support.v7.widget.CardView;

import com.example.shreeda.atmos2k16.Set.CampusMap;

*/
/**
 * Created by lakshmi sravani on 29-09-2016.
 *//*

public class Guidefragment extends Fragment {

    FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.guide_fragment, container, false);

      //  ImageButton camBt = (ImageButton) v.findViewById(R.id.reach_campus);
       // camBt.setOnClickListener(listener);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cd = (CardView) view.findViewById(R.id.card);
        cd.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v) {
                manager= getActivity().getSupportFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                Fragment fragment3 = new ReachCampus();
                transaction.replace(R.id.container,fragment3, "reach campus");
                transaction.commit();
            }
        });

        CardView cd1 = (CardView) view.findViewById(R.id.card1);
        cd1.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= manager.beginTransaction();
                Fragment fragment4 = new CampusMap();
                transaction.replace(R.id.container,fragment4, "campus map");
                transaction.commit();
            }
        });

        CardView cd2 = (CardView) view.findViewById(R.id.card2);
        cd2.setOnClickListener(new CardView.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= manager.beginTransaction();
                Fragment fragment4 = new CampusMap();
                transaction.replace(R.id.container,fragment4, "campus map");
            }
        });

    }



//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        manager = getFragmentManager();
//    }
//    ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
//
//        @Override
//        public void onClick(View arg0) {
//        //    Toast.makeText(getActivity(), "kjsadk", Toast.LENGTH_LONG).show();
//        }
//    };
//
//    public void addreachcampus() {
//        ReachCampus r = new ReachCampus();
//        FragmentTransaction Transaction = manager.beginTransaction();
//        Transaction.add(R.id.reach_campus, r, "reach_campus");
//        Transaction.commit();
//        Toast.makeText(getActivity(),"yolo",Toast.LENGTH_LONG).show();}
//

}


*/
