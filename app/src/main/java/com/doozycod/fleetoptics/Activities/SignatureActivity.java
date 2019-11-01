package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class SignatureActivity extends AppCompatActivity {
    RadioButton yesRadioBtn, noRadioBtn;
    RadioGroup radioGroup;
    Button backSignature;
    private void initUI() {
        yesRadioBtn = findViewById(R.id.yes_sign);
        noRadioBtn = findViewById(R.id.no_sign);
        radioGroup = findViewById(R.id.signRadioGroup);
        backSignature = findViewById(R.id.backSignature);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signature);
        getSupportActionBar().hide();

        initUI();
        onClickListener();
    }

    private void onClickListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (yesRadioBtn.isChecked()) {
                    startActivity(new Intent(SignatureActivity.this, SpecificRecipientActivity.class));
                }
                if (noRadioBtn.isChecked()) {
                    startActivity(new Intent(SignatureActivity.this, NotifyActivity.class));

                }
            }
        });
        backSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
