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

public class OnSignOutActivity extends AppCompatActivity implements CallbackListener {
    RecyclerView recyclerView;
    SignOutRecyclerAdapter signOutRecyclerAdapter;
    Button SignOutSubmitButton, signOutBackBtn;

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

        signOutRecyclerAdapter = new SignOutRecyclerAdapter(this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(signOutRecyclerAdapter);

        OnClickListener();
    }

    private void OnClickListener() {
        signOutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SignOutSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnSignOutActivity.this, MainActivity.class));
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
