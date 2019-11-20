package com.doozycod.fleetoptics.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.R;

public class CheckInVisitActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton meetingRadioBtn, interviewRadioBtn, personalRadioBtn;
    EditText search_emp;
    Button sumbitEmpBtn, backBtn;
    RecyclerView recyclerView;
    String purpose = "";

    private void initUI() {
        search_emp = findViewById(R.id.editTextsearchbar);
        sumbitEmpBtn = findViewById(R.id.sumbitEmpBtn);
        recyclerView = findViewById(R.id.visitor_listview);
        radioGroup = findViewById(R.id.visit_radio_group);
        meetingRadioBtn = findViewById(R.id.meeting);
        interviewRadioBtn = findViewById(R.id.interview);
        personalRadioBtn = findViewById(R.id.personal);
        backBtn = findViewById(R.id.visitCheckinBackBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_in_visit);
        getSupportActionBar().hide();

        initUI();
        onClickListener();
    }

    private void onClickListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (meetingRadioBtn.isChecked()) {
                    purpose = "Meeting";
                    meetingRadioBtn.setChecked(true);
                    interviewRadioBtn.setChecked(false);
                    personalRadioBtn.setChecked(false);

                }
                if (interviewRadioBtn.isChecked()) {
                    purpose = "Drop-in Interview";
                    meetingRadioBtn.setChecked(false);
                    interviewRadioBtn.setChecked(true);
                    personalRadioBtn.setChecked(false);
                }
                if (personalRadioBtn.isChecked()) {
                    purpose = "Personal";
                    meetingRadioBtn.setChecked(false);
                    interviewRadioBtn.setChecked(false);
                    personalRadioBtn.setChecked(true);

                }
            }
        });

//        search perform on text change update list
        search_emp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sumbitEmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if meeting is checked start EmpPersocalCheckin Activity
                if (meetingRadioBtn.isChecked()) {
                    startActivity(new Intent(CheckInVisitActivity.this, PersonalMeetingActivity.class));
                    return;
                }
//                if interview is checked start Interview Activity
                if (interviewRadioBtn.isChecked()) {
                    startActivity(new Intent(CheckInVisitActivity.this, InterviewActivity.class));
                    return;
                }
//                if personal is checked start EmpPersocalCheckin Activity
                if (personalRadioBtn.isChecked()) {
                    startActivity(new Intent(CheckInVisitActivity.this, PersonalMeetingActivity.class));
                } else {
                    Toast.makeText(CheckInVisitActivity.this, "Select Visit Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //create menu in actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_searchbar, menu);


        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();

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

    // onback action back press finish activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
