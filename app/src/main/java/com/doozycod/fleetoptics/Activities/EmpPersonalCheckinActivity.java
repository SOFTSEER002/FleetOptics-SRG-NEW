package com.doozycod.fleetoptics.Activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

public class EmpPersonalCheckinActivity extends AppCompatActivity {
    EditText fullName, companyName, emailAddress, contactPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee_personal);
        getSupportActionBar().hide();
//        fullName = findViewById(R.id.);

    }
}
