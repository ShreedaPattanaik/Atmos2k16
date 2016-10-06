package com.example.shreeda.atmos2k16;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shreeda.atmos2k16.Set.EventSet;
import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;
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

/**
 * Created by SHREEDA on 27-09-2016.
 */

public class EventDetailsActivity extends AppCompatActivity {
    EventTableManager eventTableManager;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView description, contact, prize, problem;
    ImageView Iv;
    EventSet data;
    ExitActivityTransition exitTransition;
    CardView contacts_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

/*
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.view_parent)).duration(200).start(savedInstanceState);
*/
        if (getIntent().getIntExtra("event_id", -1) == -1) {
            finish();
            return;
        }
        eventTableManager = new EventTableManager(this);
        data = eventTableManager.getEventData(getIntent().getIntExtra("event_id", 1));
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(data.getName());
            /*collapsingToolbarLayout.setTitle(data.getName());
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);*/
        }




        /*appBarLayout.setAlpha(0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(appBarLayout, "alpha", 0f, 1f);
        animator.setDuration(100);
        animator.setStartDelay(100);
        animator.start();*/
        contacts_card = (CardView) findViewById(R.id.card_contact);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Iv = (ImageView) findViewById(R.id.kens_image);
        description = (TextView) findViewById(R.id.event_descrption);
        prize = (TextView) findViewById(R.id.event_prize);
        problem = (TextView) findViewById(R.id.event_problem);
        contact = (TextView) findViewById(R.id.event_contact);

        //todo checker insertion
        description.setText(Html.fromHtml(data.getDescription()));
        prize.setText(data.getPrize());
        problem.setText(Html.fromHtml(data.getProbSt()));
        JSONArray jsonArray = data.getContacts();
        if (jsonArray != null) {
            contacts_card.setVisibility(View.VISIBLE);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = jsonArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String phone = jsonObj.getString("phone");
                    contact.setText(contact.getText() + name + " : " + phone + "\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(jsonObj);
            }

        }

        if (data.getImg_link().isEmpty()) {
            Iv.setImageResource(R.color.accent);
        } else {
            setImage();
        }


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
                    .placeholder(this.getResources().getDrawable(R.drawable.atmos16))
                    .error(this.getResources().getDrawable(R.drawable.atmos16))
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

    @Override
    protected void onDestroy() {
        eventTableManager.close();
        super.onDestroy();
    }
}


