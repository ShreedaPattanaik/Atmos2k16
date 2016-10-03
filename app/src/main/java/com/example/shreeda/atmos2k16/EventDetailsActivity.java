package com.example.shreeda.atmos2k16;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.shreeda.atmos2k16.TableManagers.EventTableManager;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
    TextView description;
    KenBurnsView kb;
    Cursor cursor;
    ExitActivityTransition exitTransition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

/*
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.view_parent)).duration(200).start(savedInstanceState);
*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);


        /*appBarLayout.setAlpha(0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(appBarLayout, "alpha", 0f, 1f);
        animator.setDuration(100);
        animator.setStartDelay(100);
        animator.start();*/

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        kb = (KenBurnsView) findViewById(R.id.kens_image);
        description = (TextView) findViewById(R.id.event_descrption);
        eventTableManager = new EventTableManager(this);
        cursor = eventTableManager.getEventData(getIntent().getIntExtra("event_id", 1));
        if (cursor != null && cursor.moveToFirst()) {
            description.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_DESCRIPTION))));
            if(cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_IMAGE_LINK)).isEmpty()){
                kb.setImageResource(R.color.accent);
            }else {
                setImage();
            }
            collapsingToolbarLayout.setTitle(cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_NAME)));
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
            cursor.close();
        }
    }
    private void setImage() {
        final String name=cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_NAME));
        final int id=cursor.getInt(cursor.getColumnIndex(EventTableManager.KEY_EVENT_ID));
        String link=cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_IMAGE_LINK));
        if (cursor.getInt(cursor.getColumnIndex(EventTableManager.KEY_IMAGE_DOWNLOAD))== 0) {
            Picasso.with(this).load(cursor.getString(cursor.getColumnIndex(EventTableManager.KEY_IMAGE_LINK)))
                    .placeholder(this.getResources().getDrawable(R.drawable.atmos16))
                    .error(this.getResources().getDrawable(R.drawable.atmos16))
                    .resize(300, 200)
                    .centerCrop()
                    .into(kb, new Callback() {
                        @Override
                        public void onSuccess() {
                            String path = saveToInternalSorage( ((BitmapDrawable) kb.getDrawable()).getBitmap(),name);
                            eventTableManager.imageDownloaded(id, path);
                        }
                        @Override
                        public void onError() {

                        }
                    });
        } else {
            kb.setImageBitmap(loadImageFromStorage(link,name));
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
        cursor.close();
        eventTableManager.close();
        super.onDestroy();
    }
}


