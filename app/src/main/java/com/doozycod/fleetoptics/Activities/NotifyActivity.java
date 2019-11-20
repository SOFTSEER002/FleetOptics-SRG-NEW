package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class NotifyActivity extends AppCompatActivity {
    TextView message, message2;

    //    typecasting method
    private void initUI() {
        message = findViewById(R.id.message);
        message2 = findViewById(R.id.message2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notify);

//        hide actionbar
        getSupportActionBar().hide();
//         typecasting
        initUI();
//        if user is returning from signature Activity
        if (getIntent().hasExtra("signature")) {
            message.setText("The recipient has been notified… Please wait.");
            message2.setText("Someone will be with you shortly to receive the package… Thank You!");
        }
//        if user is returning from multiple visitor section
        if (getIntent().hasExtra("multiple")) {
            message.setText("John has been notified and will be with you shortly… Thank You!");
            message2.setText("Sorry. John is not available at this time. Please try again later." +
                    "Thank you for stopping by!");
        }
//        if visitor is delivering something
        else {
            message.setText("Please wait while we retrieve someone for you.");
            message2.setText("Someone will be with you shortly to receive the package… Thank You!");
        }

//        handler to return to home screen after ten seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(NotifyActivity.this, SplashActivity.class));
            }
        }, 10000);
    }

    @Override
    public void onBackPressed() {

       /* startActivity(new Intent(NotifyActivity.this, HomeActivity.class));
        finishAffinity();*/
    }


}
