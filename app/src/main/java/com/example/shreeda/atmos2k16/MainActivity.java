package com.example.shreeda.atmos2k16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shreeda.atmos2k16.Set.CampusMap;

import Helper.RecyclerClickListener;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout mDrawer;
    CustomActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigation;
    FragmentManager manager;
    int backNumber;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer, toolbar);
        mNavigation = (NavigationView) findViewById(R.id.navigationView);
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        manager = getSupportFragmentManager();
        setHomeFragment();
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                manager.popBackStack("events", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction transaction;
                Fragment fragment;

                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        setHomeFragment();

                        break;


                    case R.id.schedule:
                        transaction = manager.beginTransaction();
                        Fragment fragment1 = new CampusMap();
                        transaction.replace(R.id.container, fragment1, "campusmap");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);
                        break;

                    case R.id.register:
                        transaction = manager.beginTransaction();
                        Intent intent = new Intent(MainActivity.this, Register.class);
                        startActivity(intent);
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);
                        fab.hide();


                        break;
                    case R.id.events:
                    /*transaction =manager.beginTransaction()

                    transaction.commit();*/
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);
                        startActivity(new Intent(MainActivity.this, EventDetailsActivity.class));

                        break;


                    case R.id.feed:
                   /* transaction =manager.beginTransaction();
                    transaction.commit();
                    menuItem.setChecked(true);*/
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.Guide:

                        transaction = manager.beginTransaction();

                        fragment = new Guidefragment();
                        transaction.replace(R.id.container, fragment, "events");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;


                    case R.id.AppCredits:

                       /* transaction = manager.beginTransaction();
                        transaction.commit();
                        menuItem.setChecked(true);*/
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.Sponsers:
                        transaction = manager.beginTransaction();
                        fragment = new SponsorsFragment();
                        transaction.replace(R.id.container, fragment, "Sponsors");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;


                }

                return false;
            }
        });

    }

    private void setHomeFragment() {
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment, "home");
        transaction.commit();
        mNavigation.getMenu().getItem(0).setChecked(true);
        mDrawer.closeDrawer(Gravity.LEFT);
    }


    @Override
    protected void onResume() {
        super.onResume();
        backNumber = 0;
    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
            mDrawer.closeDrawer(Gravity.LEFT);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (backNumber < 1) {
            backNumber++;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backNumber = 0;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {
        public CustomActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
            super(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        }
    }



}
