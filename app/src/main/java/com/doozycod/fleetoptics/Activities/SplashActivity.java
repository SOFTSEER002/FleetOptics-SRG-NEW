package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    // Animation
    Animation animFadein;
    Animation animFadeout;
    RelativeLayout mainLayout;
    TextView text_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set activity as FULL SCREEn/ hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

//        hide Actionbar
        getSupportActionBar().hide();

        mainLayout = findViewById(R.id.splash);
        text_signin = findViewById(R.id.text_signin);

        //        Animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        animFadeout.setAnimationListener(this);
        animFadein.setAnimationListener(this);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
//        start Home Activity after 3 seconds of delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text_signin.startAnimation(animFadeout);
            }
        }, 1000);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
