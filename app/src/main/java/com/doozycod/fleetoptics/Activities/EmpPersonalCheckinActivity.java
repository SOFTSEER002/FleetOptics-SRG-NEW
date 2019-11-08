package com.doozycod.fleetoptics.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class EmpPersonalCheckinActivity extends AppCompatActivity {
    EditText fullName, companyName, emailAddress, contactPhoneNo;
    Button meetBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee_personal);
        getSupportActionBar().hide();

        meetBackBtn = findViewById(R.id.meetBackBtn);
        meetBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
