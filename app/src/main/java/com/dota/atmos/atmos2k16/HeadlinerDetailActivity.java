package com.dota.atmos.atmos2k16;

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

public class HeadlinerDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    int pos;//// TODO: 07-10-2016  intialize it
    TextView mdescription, mcontact;
    ImageView Iv;
    String description = "<h2>Event Description</h2><p>Register in teams of two.</br>A case study will be provided 2 days prior to the competition. <br>Role of Plaintiff: Put up relevant arguments against the concerned firm.<br>Role of Defendant: Defend its maneuver, supported by relevant arguments. <br>The next day, teams choose to be the defendant or the plaintiff, entirely based on first come first basis. (This will be kept hidden from the participants.)On the day of the debate, each team will be paired with another (same side for that particular case). Hence forming teams of four, in a set of 8 for a particular case study. </p> <h2>Format Of Debate</h2> <p>Each contestant speaks for 2 minutes at most (minimum 1 minute), giving the whole set 16 minutes.Followed by a Cross-Questioning session between the two teams lasting 4 minutes.Followed by a Question-Answer session with the audience lasting 2 minutes. </p> <h2>Judging Criteria</h2> <ul> <li>Speaking Skills</li> <li>Argument relevance </li> <li>Spontaneity </li> <li>Body Language </li> <li>Marks to be deducted for a speech less than one minute.</li> <li>The ability of the team members to merge their content(continuity and  &nbsp;&nbsp;&nbsp; consistency of arguments). </li> <li>Marks to be deducted for excessive redundancy. </li> <li>Cross-questioning Ethics . </li> </ul><br> <b>Contact Details: </b><br>Aravind A S : +91 9553325392";
    String contacts = "contacts";
    JSONArray array = new JSONArray();
    JSONObject object = new JSONObject();
    String[] imgURL = {"http://www.bits-atmos.org/Events/img/miniGp.jpg",
            "http://www.bits-atmos.org/Events/img/robowars.jpg",
            "http://www.bits-atmos.org/Events/img/chemEcar-01.jpg",
            "http://www.bits-atmos.org/Events/img/MazePerilous-2-01.jpg",
            "http://www.bits-atmos.org/Events/img/quadcopter.jpg",
            "http://www.bits-atmos.org/Events/img/TechExpo1-01.jpg",
            "http://www.bits-atmos.org/Events/img/TechExpo1-01.jpg"};
    final String[] nameArray = {"MINI GP", "ROBOWARS", "CHEME CAR", "MAZE PERILOUS", "QUADCOPTER", "TECH EXPO", "PYBITS"};
    ExitActivityTransition exitTransition;
    CardView contacts_card;
    private String Tag="HeadlinerDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos=getIntent().getIntExtra("pos",0);
        setContentView(R.layout.activity_headliner_detail);
        try {
            object.put("name", "harshit");
            object.put("number", "1234567890");
            array.put(object);
            Log.e("ScheduleUpdateService", array.toString());
            JSONObject object1 = new JSONObject();
            object1.put("name", "Rajat");
            object1.put("number", "0987456321");
            array.put(object1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

/*
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.view_parent)).duration(200).start(savedInstanceState);
*/
    /*    if (getIntent().getIntExtra("event_id", -1) == -1) {
            finish();
            return;
        }*/
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(nameArray[pos]);//// TODO: 07-10-2016 change name
            /*collapsingToolbarLayout.setTitle(data.getName());
            collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);*/
        }

        contacts_card = (CardView) findViewById(R.id.card_contact);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Iv = (ImageView) findViewById(R.id.kens_image);
        mdescription = (TextView) findViewById(R.id.event_descrption);
        mcontact = (TextView) findViewById(R.id.event_contact);

        //todo checker insertion
        mdescription.setText(Html.fromHtml(description));//// TODO: 07-10-2016  change it
        JSONArray jsonArray = array;//// TODO: 07-10-2016 change it
        if (jsonArray != null) {
            contacts_card.setVisibility(View.VISIBLE);

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String phone = jsonObj.getString("number");
                    mcontact.setText(mcontact.getText() + name + " : " + phone + "\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    /*    if (data.getImg_link().isEmpty()) {
            Iv.setImageResource(R.color.accent);
        } else {
            setImage();
        }*/

        setImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void setImage() {
        final String name = nameArray[pos];
        String link = imgURL[pos];

        Picasso.with(this).load(imgURL[pos])
                .placeholder(this.getResources().getDrawable(R.drawable.default_card_image))
                .error(this.getResources().getDrawable(R.drawable.default_card_image))
                .resize(300, 200)
                .centerCrop()
                .into(Iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        //// TODO: 07-10-2016  save img
                        String path = saveToInternalSorage(((BitmapDrawable) Iv.getDrawable()).getBitmap(), name);
                        Log.e(Tag,"onSuccess");
                        //eventTableManager.imageDownloaded(id, path);
                    }

                    @Override
                    public void onError() {

                    }
                });

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
