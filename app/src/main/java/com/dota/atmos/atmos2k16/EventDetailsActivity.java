package com.dota.atmos.atmos2k16;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dota.atmos.atmos2k16.Set.EventSet;
import com.dota.atmos.atmos2k16.Set.ScheduleSet;
import com.dota.atmos.atmos2k16.TableManagers.EventTableManager;
import com.dota.atmos.atmos2k16.TableManagers.ScheduleTableManager;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Helper.Share;

/**
 * Created by SHREEDA on 27-09-2016.
 */

public class EventDetailsActivity extends AppCompatActivity {
    EventTableManager eventTableManager;
    FloatingActionButton fab;
    CardView linkLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView description, contact, prize, problem;
    ImageView Iv;
    EventSet data;
    ExitActivityTransition exitTransition;
    CardView contacts_card, prize_card, description_card, problem_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

/*
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.view_parent)).duration(200).start(savedInstanceState);
*/
        linkLayout = (CardView) findViewById(R.id.linklayout);
        if (getIntent().getIntExtra("event_id", -1) == -1) {
            finish();
            return;
        }
        eventTableManager = new EventTableManager(this);
        data = eventTableManager.getEventData(getIntent().getIntExtra("event_id", 1));
        if (data == null) {
            finish();
            return;
        }
        fab = (FloatingActionButton) findViewById(R.id.fab_share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.shareData(data, EventDetailsActivity.this);
            }
        });
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(data.getName());
        }

        contacts_card = (CardView) findViewById(R.id.card_contact);
        prize_card = (CardView) findViewById(R.id.card_prize);
        problem_card = (CardView) findViewById(R.id.card_problem);
        description_card = (CardView) findViewById(R.id.card_description);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Iv = (ImageView) findViewById(R.id.kens_image);
        description = (TextView) findViewById(R.id.event_descrption);
        prize = (TextView) findViewById(R.id.event_prize);
        problem = (TextView) findViewById(R.id.event_problem);
        contact = (TextView) findViewById(R.id.event_contact);


        if (data.getDescription().equals("")) {
            description_card.setVisibility(View.GONE);
        } else description.setText(Html.fromHtml(data.getDescription()));

        if (data.getPrize() == null || data.getPrize().equals("")) {
            prize_card.setVisibility(View.GONE);
        } else prize.setText(data.getPrize());
        if (data.getProbSt() == null || data.getProbSt().equals("")) {
            problem_card.setVisibility(View.GONE);
        } else if (data.getPdfLink() == null || data.getPdfLink().equals("")) {
            problem.setText(Html.fromHtml(data.getProbSt()));
            linkLayout.setVisibility(View.GONE);
        } else {
            problem.setText(Html.fromHtml(data.getProbSt()));
            linkLayout.setVisibility(View.VISIBLE);
            linkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getPdfLink()));
                    startActivity(browserIntent);
                }
            });
        }

        LinearLayout scheduleContainer = (LinearLayout) findViewById(R.id.scheduleContainer);
        ScheduleTableManager scheduleTableManager = new ScheduleTableManager(this);
        final ArrayList<ScheduleSet> sets = scheduleTableManager.getSchedule(data.getId());
        scheduleContainer.removeAllViews();
        if (sets.size() == 0) {
            findViewById(R.id.scheduleCard).setVisibility(View.GONE);
        }

        for (int i = 0; i < sets.size(); i++) {
            final ScheduleSet set = sets.get(i);
            View v = LayoutInflater.from(this).inflate(R.layout.event_detail_timeline, scheduleContainer, false);
            ((TextView) v.findViewById(R.id.round_name)).setText(set.getRound());
            ((TextView) v.findViewById(R.id.venue)).setText(set.getVenue());
            ((TextView) v.findViewById(R.id.time)).setText(getTime(set.getTime()));
            v.setBackground(null);
            scheduleContainer.addView(v);
            if (i != sets.size() - 1) {
                View divider = LayoutInflater.from(this).inflate(R.layout.divider, scheduleContainer, false);
                scheduleContainer.addView(divider);
            }
        }

        JSONArray jsonArray = data.getContacts();
        if (jsonArray != null) {
            contacts_card.setVisibility(View.VISIBLE);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String phone = jsonObj.getString("number");
                    if (name.equals("") || phone.equals("")) {

                    } else contact.setText(contact.getText() + name + " : " + phone + "\n");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        if (data.getImg_link().isEmpty()) {
            Iv.setImageResource(R.color.accent);
        } else {
            setImage();
        }


    }

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM hh:mm a");
        return dateFormat.format(calendar.getTime());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void setImage() {
        final String name = data.getName();
        final int id = data.getId();
        String link = data.getImg_link();
        if (data.isImage_downloaded() == false) {
            Picasso.with(this).load(data.getImg_link())
                    .placeholder(this.getResources().getDrawable(R.drawable.default_card_image))
                    .error(this.getResources().getDrawable(R.drawable.default_card_image))
                    .resize(300, 200)
                    .centerCrop()
                    .into(Iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            String path = saveToInternalSorage(((BitmapDrawable) Iv.getDrawable()).getBitmap(), name);
                            eventTableManager.imageDownloaded(id, path);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            Iv.setImageBitmap(loadImageFromStorage(link, name));
        }
    }

    private String saveToInternalSorage(Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("Save unsuccessful", e.toString());
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String path, String name) {

        try {
            File f = new File(path, name + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
