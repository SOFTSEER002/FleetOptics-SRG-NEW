package com.doozycod.fleetoptics.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    // Animation
    Animation animFadein;
    Animation animFadeout;
    RelativeLayout main_screen;
    LinearLayout SigninSignoutButton;
    TextView text_signin;
    Button signinButton, signoutButton;

    private void initUI() {
        main_screen = findViewById(R.id.main_screen);
        SigninSignoutButton = findViewById(R.id.SigninSignoutButton);
        text_signin = findViewById(R.id.text_signin);
        signinButton = findViewById(R.id.signinBtn);
        signoutButton = findViewById(R.id.signoutButton);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

//      Permission for Camera and Storage
        permissionCheck();
//          typecasting
        initUI();

//        Animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeout = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        animFadeout.setAnimationListener(this);
        animFadein.setAnimationListener(this);
        onClickListeners();
    }

    private void onClickListeners() {
        main_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                animFadeout.start();
                text_signin.startAnimation(animFadeout);
                main_screen.setEnabled(false);

            }
        });
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CheckinTypeActivity.class));
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnSignOutActivity.class));
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animFadeout) {
            text_signin.setVisibility(View.GONE);
            SigninSignoutButton.setVisibility(View.VISIBLE);
            SigninSignoutButton.startAnimation(animFadein);
        }


    }
    private void permissionCheck() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

            }



        }).check();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition( R.anim.slide_in,R.anim.slide_out);

    }
}
