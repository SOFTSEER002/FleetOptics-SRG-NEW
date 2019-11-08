package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class InterviewActivity extends AppCompatActivity {
    CheckBox checkBox;
    Button submitButton, interBackBtn;

    private void initUI() {
        checkBox = findViewById(R.id.checkBox);
        submitButton = findViewById(R.id.submitInterButton);
        interBackBtn = findViewById(R.id.interBackBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_interview);
        getSupportActionBar().hide();
        initUI();
        submitButton.setEnabled(false);
        onClickListener();
    }

    private void onClickListener() {

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    submitButton.setEnabled(true);
                } else {
                    submitButton.setEnabled(false);

                }
            }
        });
        interBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterviewActivity.this, CameraActivity.class));
            }
        });
    }


}
