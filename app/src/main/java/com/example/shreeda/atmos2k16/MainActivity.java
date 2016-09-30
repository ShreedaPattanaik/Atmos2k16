package com.example.shreeda.atmos2k16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import Helper.RecyclerClickListener;



public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout mDrawer;
    CustomActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigation;
    FragmentManager manager;
    int backNumber;
    RecyclerView mRecyclerView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//


        setContentView(R.layout.activity_main);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        final MyAdapter mAdapter = new MyAdapter(this);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        mRecyclerView.setAdapter(mAdapter);
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
        FragmentTransaction transaction2 = manager.beginTransaction();

        transaction2.commit();


        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            manager.popBackStack("events", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment;

            switch (menuItem.getItemId()) {
                case R.id.home_menu:
                    fragment = new HomeFragment();
                    transaction.replace(R.id.container, fragment, "home");
                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);

                    break;


                case R.id.schedule:

                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.register:
                    Intent intent = new Intent(MainActivity.this, Register.class);
                    startActivity(intent);
                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);
                    fab.hide();


                    break;
                case R.id.events:


                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);
                    startActivity(new Intent(MainActivity.this, EventDetailsActivity.class));

                    break;


                case R.id.feed:

                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);

                    break;

                case R.id.Guide:


                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);

                    break;


                case R.id.AppCredits:


                    transaction.commit();
                    menuItem.setChecked(true);
                    mDrawer.closeDrawer(Gravity.LEFT);

                    break;

                case R.id.Sponsers:

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.home) {
            mDrawer.openDrawer(Gravity.LEFT);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    RecyclerClickListener clickListener=new RecyclerClickListener() {
        @Override
        public void onClick(View v, int pos) {
            switch (pos){
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:



            }
        }
    };




    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton= (ImageButton) itemView.findViewById(R.id.main_events);
            if(clickListener!=null){
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onClick(v, getLayoutPosition());
                    }
                });
            }
        }
    }
    LayoutInflater inflater;

    class  MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        Context context;
        int[] resources;


        public MyAdapter(MainActivity context) {
            this.context = context;
            inflater= LayoutInflater.from(context);
            resources= new int[]{R.drawable.events, R.drawable.events, R.drawable.events, R.drawable.events, R.drawable.events};

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_main_row,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.imageButton.setImageResource(resources[position]);
        }

        @Override
        public int getItemCount() {
            return resources.length;
        }
    }
}