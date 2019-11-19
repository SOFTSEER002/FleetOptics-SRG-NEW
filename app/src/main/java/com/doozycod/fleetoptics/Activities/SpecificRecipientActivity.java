package com.doozycod.fleetoptics.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.Adapter.RecyclerAdapter;
import com.doozycod.fleetoptics.R;

public class SpecificRecipientActivity extends AppCompatActivity {
    Button submitButton;
    RecyclerView recycler_view;
    RecyclerAdapter recyclerAdapter;

    private void initUI() {

        submitButton = findViewById(R.id.submitButton);
        recycler_view = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_specific_recipient);

//      typecasting
        initUI();

//         Recyclerview Properties
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(recyclerAdapter);
//        onClick events
        ClickListener();
    }

    private void ClickListener() {
//        take to Notify activity and set signture msg
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecificRecipientActivity.this, NotifyActivity.class);
                intent.putExtra("signature","must");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        custom main bar layout
        getMenuInflater().inflate(R.menu.menu_with_searchbar, menu);

//        search bar on actionbar
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
//          start search manager service
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
