package com.doozycod.fleetoptics.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.Adapter.RecyclerAdapter;
import com.doozycod.fleetoptics.Interface.CallbackListener;
import com.doozycod.fleetoptics.Model.GetEmployeeModel;
import com.doozycod.fleetoptics.R;
import com.doozycod.fleetoptics.Service.ApiService;
import com.doozycod.fleetoptics.Service.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInVisitActivity extends AppCompatActivity implements CallbackListener {
    RadioGroup radioGroup;
    RadioButton meetingRadioBtn, interviewRadioBtn, personalRadioBtn;
    EditText search_emp;
    Button sumbitEmpBtn, backBtn;
    RecyclerView recyclerView;
    String purpose = "";
    RecyclerAdapter recyclerAdapter;
    ApiService apiService;
    List<GetEmployeeModel.employees> getEmployeeModels = new ArrayList<>();
    String employeeName, empId;

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
        apiService = ApiUtils.getAPIService();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//          get Employee data
        getEmployees();
    }

    private void onClickListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (meetingRadioBtn.isChecked()) {
                    purpose = "Meeting";
                    if (!search_emp.isEnabled()) {
                        search_emp.setText("");
                    }
                    search_emp.setEnabled(true);
                    recyclerView.setEnabled(true);
                    meetingRadioBtn.setChecked(true);
                    interviewRadioBtn.setChecked(false);
                    personalRadioBtn.setChecked(false);

                }
                if (interviewRadioBtn.isChecked()) {
                    purpose = "Drop-in Interview";
                    search_emp.setText("");
                    search_emp.setEnabled(false);
                    recyclerView.setEnabled(false);
                    meetingRadioBtn.setChecked(false);
                    interviewRadioBtn.setChecked(true);
                    personalRadioBtn.setChecked(false);
                }
                if (personalRadioBtn.isChecked()) {
                    purpose = "Personal";
                    search_emp.setEnabled(true);
                    if (!search_emp.isEnabled()) {
                        search_emp.setText("");
                    }
                    recyclerView.setEnabled(true);
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
                if (charSequence.toString().isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                filter(charSequence.toString());

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
                    Intent intent = new Intent(CheckInVisitActivity.this, PersonalMeetingActivity.class);
                    intent.putExtra("purpose", "Meeting");
                    intent.putExtra("empId", empId);
                    startActivity(intent);
                    return;
                }
//                if interview is checked start Interview Activity
                if (interviewRadioBtn.isChecked()) {
                    Intent intent = new Intent(CheckInVisitActivity.this, InterviewActivity.class);
                    intent.putExtra("purpose", "Interview");
                    startActivity(intent);
                    return;
                }
//                if personal is checked start EmpPersocalCheckin Activity
                if (personalRadioBtn.isChecked()) {
                    Intent intent = new Intent(CheckInVisitActivity.this, PersonalMeetingActivity.class);
                    intent.putExtra("purpose", "Personal");
                    intent.putExtra("empId", empId);
                    startActivity(intent);
                } else {
                    Toast.makeText(CheckInVisitActivity.this, "Select Visit Type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void filter(String text) {
        List<GetEmployeeModel.employees> temp = new ArrayList();
        for (GetEmployeeModel.employees d : getEmployeeModels) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getFull_name().toLowerCase().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        recyclerAdapter.updateList(temp);
    }

    void getEmployees() {
        apiService.getAllEmployees().enqueue(new Callback<GetEmployeeModel>() {
            @Override
            public void onResponse(Call<GetEmployeeModel> call, Response<GetEmployeeModel> response) {
                if (response.isSuccessful()) {

                    getEmployeeModels = response.body().getEmployees();
                    for (int i = 0; i < getEmployeeModels.size(); i++) {
                        Log.e("Employee DATA", "onResponse: " + getEmployeeModels.get(i).getId());
                        recyclerAdapter = new RecyclerAdapter(CheckInVisitActivity.this, getEmployeeModels, CheckInVisitActivity.this);
//                        recyclerView.setAdapter(recyclerAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeModel> call, Throwable t) {

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

    @Override
    public void onResultListener(String RecipientName, String id) {
        employeeName = RecipientName;
        empId = id;
        Log.e("NAME", "onResultListener: " + employeeName + "  " + empId);

    }
}
