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

public class PackageDeliveryActivity extends AppCompatActivity {
    RadioButton yesRadioBtn, noRadioBtn;
    RadioGroup radioGroup;
    Button backPackageDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_package_delivery);
        getSupportActionBar().hide();

//      typecasting
        initUI();
//        on click events

        onClickListener();
    }

    private void onClickListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (yesRadioBtn.isChecked()) {
                    startActivity(new Intent(PackageDeliveryActivity.this, SignatureActivity.class));
                }
                if (noRadioBtn.isChecked()) {
                    startActivity(new Intent(PackageDeliveryActivity.this, NotifyActivity.class));
                }
            }
        });
        backPackageDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void initUI() {
        yesRadioBtn = findViewById(R.id.yes_radio);
        noRadioBtn = findViewById(R.id.no_radio);
        radioGroup = findViewById(R.id.radioGroup);
        backPackageDelivery = findViewById(R.id.backPackageDelivery);
    }

}
