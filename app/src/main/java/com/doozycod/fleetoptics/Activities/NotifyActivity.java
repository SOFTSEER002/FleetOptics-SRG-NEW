package com.doozycod.fleetoptics.Activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class NotifyActivity extends AppCompatActivity {
    TextView message, message2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notify);

//        hide actionbar
        getSupportActionBar().hide();
//         typecasting
        initUI();
        if (getIntent().hasExtra("signature")) {
            message.setText("The recipient has been notified… Please wait.");
            message2.setText("Someone will be with you shortly to receive the package… Thank You!");
        } else {
            message.setText("Please wait while we retrieve someone for you.");
            message2.setText("Someone will be with you shortly to receive the package… Thank You!");
        }
    }

    private void initUI() {
        message = findViewById(R.id.message);
        message2 = findViewById(R.id.message2);
    }
}
