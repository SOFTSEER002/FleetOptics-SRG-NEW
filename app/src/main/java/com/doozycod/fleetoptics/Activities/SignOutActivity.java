package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.Adapter.SignOutRecyclerAdapter;
import com.doozycod.fleetoptics.Interface.CallbackListener;
import com.doozycod.fleetoptics.R;

public class SignOutActivity extends AppCompatActivity implements CallbackListener {
    RecyclerView recyclerView;
    SignOutRecyclerAdapter signOutRecyclerAdapter;
    Button SignOutSubmitButton, signOutBackBtn;

//    typecasting method
    private void initUI() {
        recyclerView = findViewById(R.id.signoutRecyclerView);
        SignOutSubmitButton = findViewById(R.id.SignOutSubmitButton);
        signOutBackBtn = findViewById(R.id.signOutBackBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_sign_out);
        getSupportActionBar().hide();

//        Typecasting
        initUI();

//        recyclerview properties
        recyclerviewAdapter();

//        on Click Listener
        OnClickListener();
    }

//    rv properties method
    private void recyclerviewAdapter() {
        signOutRecyclerAdapter = new SignOutRecyclerAdapter(this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(signOutRecyclerAdapter);
    }


    private void OnClickListener() {
//        finish Activity
        signOutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        start Home ACtivity on sign out submit button
        SignOutSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignOutActivity.this, SplashActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onResultListener(String RecipientName, String type) {
        if (!type.isEmpty()) {
            SignOutSubmitButton.setEnabled(true);
        }
    }
}
