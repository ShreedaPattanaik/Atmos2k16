package com.dota.atmos.atmos2k16;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.dota.atmos.atmos2k16.TableManagers.EventsFragment;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout mDrawer;
    CustomActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigation;
    FragmentManager manager;
    int backNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

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
                        getSupportActionBar().setTitle("Schedule");
                        transaction = manager.beginTransaction();
                        Fragment fragment3 = new ScheduleFragment();
                        transaction.replace(R.id.container, fragment3, "Timeline");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.register:
                        getSupportActionBar().setTitle("Registration");
                        transaction = manager.beginTransaction();
                        Fragment fragment2 = new RegisterFragment();
                        transaction.replace(R.id.container, fragment2, "campusmap");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);



                        break;
                    case R.id.events:

                        transaction = manager.beginTransaction();
                        Fragment Events = new EventsFragment();
                        transaction.replace(R.id.container, Events, "events");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);


                        break;


                    case R.id.feed:
                        getSupportActionBar().setTitle("Feed");
                        transaction = manager.beginTransaction();
                        Fragment Feed = new FeedFragment();
                        transaction.replace(R.id.container, Feed, "feed");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.Guide:
                        getSupportActionBar().setTitle("Guide");
                        transaction = manager.beginTransaction();

                        fragment = new Guidefragment();
                        transaction.replace(R.id.container, fragment, "events");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;


                    case R.id.AppCredits:
                        getSupportActionBar().setTitle("Credits");
                       /* transaction = manager.beginTransaction();
                        transaction.commit();
                        menuItem.setChecked(true);*/
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;

                    case R.id.Contactus:
                        getSupportActionBar().setTitle("Contact us");
                        transaction = manager.beginTransaction();
                        fragment = new ContactsFragment();
                        transaction.replace(R.id.container, fragment, "ContactUs");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);
                        break;


                    case R.id.Sponsors:
                        getSupportActionBar().setTitle("Sponsors");
                        transaction = manager.beginTransaction();
                        fragment = new SponsorsFragment();
                        transaction.replace(R.id.container, fragment, "Sponsors");
                        transaction.commit();
                        menuItem.setChecked(true);
                        mDrawer.closeDrawer(Gravity.LEFT);

                        break;
                    case  R.id.favourites:
                        getSupportActionBar().setTitle("Favourites");
                        transaction = manager.beginTransaction();
                        fragment = new FavouriteFragment();
                        transaction.replace(R.id.container, fragment, "favourite");
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
        getSupportActionBar().setTitle("Atmos");
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
        } else if (((HomeFragment) getSupportFragmentManager().findFragmentByTag("home")) == null) {
            setHomeFragment();

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
