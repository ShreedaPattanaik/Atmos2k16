package com.example.shreeda.atmos2k16;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;


/**
 * Created by lakshmi sravani on 29-09-2016.
 */
public class Guidefragment extends Fragment {

    FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guide, container, false);

      //  ImageButton camBt = (ImageButton) v.findViewById(R.id.reach_campus);
       // camBt.setOnClickListener(listener);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getFragmentManager();
    }
    ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {

        @Override
        public void onClick(View arg0) {
        //    Toast.makeText(getActivity(), "kjsadk", Toast.LENGTH_LONG).show();
        }
    };

    public void addreachcampus() {
        ReachCampus r = new ReachCampus();
        FragmentTransaction Transaction = manager.beginTransaction();
        Transaction.add(R.id.reach_campus, r, "reach_campus");
        Transaction.commit();
        Toast.makeText(getActivity(),"yolo",Toast.LENGTH_LONG).show();}


}


