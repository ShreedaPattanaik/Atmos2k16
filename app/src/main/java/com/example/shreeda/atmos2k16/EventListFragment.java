package com.example.shreeda.atmos2k16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;

import java.util.ArrayList;

public class EventListFragment extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    LinearLayout onHaveEvents, onNoEvents;
    EventTableManager eventTableManager;
    ArrayList<String> tab;
    private String Tag="EventListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fargment_event_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onHaveEvents = (LinearLayout) view.findViewById(R.id.haveevent);
        onNoEvents = (LinearLayout) view.findViewById(R.id.noevent);
        tab = new ArrayList<String>();
        eventTableManager = new EventTableManager(getActivity());
        tab = eventTableManager.getDistinctTabs(1); //todo change
        Log.e(Tag+"tabv",tab.toString());
        if (tab.isEmpty()) {
            onNoEvents.setVisibility(View.VISIBLE);
            onHaveEvents.setVisibility(View.GONE);
        } else {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
            tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
            mViewPager = (ViewPager) view.findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putString("tab", tab.get(position));
            EventPagerFragment frag = new EventPagerFragment();
            frag.setArguments(args);
            return frag;
        }

        @Override
        public int getCount() {
            return tab.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab.get(position);
        }
    }
}
