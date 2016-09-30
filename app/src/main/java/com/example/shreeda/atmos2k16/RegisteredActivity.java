package com.example.shreeda.atmos2k16;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.example.shreeda.atmos2k16.Register.KEY_SELECTED;

/**
 * Created by SHREEDA on 29-09-2016.
 */

public class RegisteredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_activity);


        String selected;
        Intent intent1 = getIntent();

        if (intent1 == null) {

            selected = intent1.getStringExtra(KEY_SELECTED);
            TextView eventnames = (TextView) findViewById(R.id.eventnames);
            eventnames.setText(selected);
        }

    }
}
