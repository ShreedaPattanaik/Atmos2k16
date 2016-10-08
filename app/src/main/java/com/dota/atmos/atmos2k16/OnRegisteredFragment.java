package com.dota.atmos.atmos2k16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by SHREEDA on 01-10-2016.
 */

public class OnRegisteredFragment extends Fragment {

    private static final String ARG_PARAM1 = "eventname";
    private static final String ARG_PARAM2 = "atmosId";
    private static final String ARG_PARAM3 = "email";
    TextView event_tv, atmodId_tv, email_tv;
    private String mEvents, mID, mEmail;

    public static OnRegisteredFragment newInstance(String eventnames, String atmosId, String email) {
        OnRegisteredFragment fragment = new OnRegisteredFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, eventnames);
        args.putString(ARG_PARAM2, atmosId);
        args.putString(ARG_PARAM3, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        if (getArguments() != null) {
            mEvents = getArguments().getString(ARG_PARAM1);
            String[] array = mEvents.split(",");
            Set<String> set = new HashSet<>();
            for (int i = 0; i < array.length; i++) {
                set.add(array[i].toUpperCase().trim());
            }
            mEvents = set.toString();
            mEvents=mEvents.substring(1,mEvents.length()-1);
            Log.e("OnRegister", mEvents);
            mID = getArguments().getString(ARG_PARAM2);
            mEmail = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.registered_activity, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        event_tv = (TextView) view.findViewById(R.id.EVENTList);
        atmodId_tv = (TextView) view.findViewById(R.id.REGID);
        email_tv = (TextView) view.findViewById(R.id.EMAIL);
        event_tv.setText(mEvents);
        atmodId_tv.setText(mID);
        email_tv.setText(mEmail);
    }

}
