package com.doozycod.fleetoptics.Activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.Adapter.SignOutRecyclerAdapter;
import com.doozycod.fleetoptics.R;

public class OnSignOutActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SignOutRecyclerAdapter signOutRecyclerAdapter;
    Button SignOutSubmitButton;
    private void initUI() {
        recyclerView = findViewById(R.id.signoutRecyclerView);
        SignOutSubmitButton = findViewById(R.id.SignOutSubmitButton);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_sign_out);
        getSupportActionBar().hide();

//        Typecasting
        initUI();

        signOutRecyclerAdapter = new SignOutRecyclerAdapter(this);


        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(signOutRecyclerAdapter);
    }


}
