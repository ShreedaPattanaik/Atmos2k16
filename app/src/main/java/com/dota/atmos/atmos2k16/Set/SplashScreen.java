package com.dota.atmos.atmos2k16.Set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.dota.atmos.atmos2k16.MainActivity;
import com.dota.atmos.atmos2k16.R;
import com.dota.atmos.atmos2k16.Services.ScheduleUpdateService;
import com.squareup.picasso.Picasso;

/**
 * Created by SHREEDA on 26-09-2016.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Picasso.with(this).load(R.drawable.landinglogo)
                .fit().centerInside()
                .into((ImageView) findViewById(R.id.logoContainer));
        Picasso.with(this).load(R.drawable.cyient)
                .fit().centerInside()
                .into((ImageView) findViewById(R.id.title));
        Picasso.with(this).load(R.drawable.wat)
                .fit().centerInside()
                .into((ImageView) findViewById(R.id.coPower1));
        Picasso.with(this).load(R.drawable.prodigy)
                .fit().centerInside()
                .into((ImageView) findViewById(R.id.coPower2));
        Picasso.with(this).load(R.drawable.sbi)
                .fit().centerInside()
                .into((ImageView) findViewById(R.id.association));

        View[] v = {findViewById(R.id.logoContainer), findViewById(R.id.title), findViewById(R.id.titleText),
                findViewById(R.id.coPoweredText),
                findViewById(R.id.coPower1), findViewById(R.id.coPower2),
                findViewById(R.id.inAssociationText), findViewById(R.id.association)};

        Animation[] animation = new Animation[v.length];
        for (int i = 0; i < v.length; i++) {
            animation[i] = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation[i].setDuration(300);
            animation[i].setStartOffset(0);
            animation[i].setInterpolator(new DecelerateInterpolator(1.5f));
            animation[i].setFillAfter(true);
        }
        animation[0].setDuration(300);
        animation[0].setStartOffset(1000);
        v[0].startAnimation(animation[0]);

        for (int i = 1; i < v.length; i++)
            v[i].startAnimation(animation[i]);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                // close this activity
            }
        }, 2000);

        service();
    }

    private void service() {
        Intent intent = new Intent(this, ScheduleUpdateService.class);
        startService(intent);
    }
}

