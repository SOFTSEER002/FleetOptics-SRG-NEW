package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.Adapter.RecyclerAdapter;
import com.doozycod.fleetoptics.Interface.CallbackListener;
import com.doozycod.fleetoptics.Model.GetEmployeeModel;
import com.doozycod.fleetoptics.Model.ResultModel;
import com.doozycod.fleetoptics.R;
import com.doozycod.fleetoptics.Service.ApiService;
import com.doozycod.fleetoptics.Service.ApiUtils;
import com.doozycod.fleetoptics.Utils.CustomProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificRecipientActivity extends AppCompatActivity implements CallbackListener {
    Button submitButton;
    RecyclerView recycler_view;
    RecyclerAdapter recyclerAdapter;
    String checkin_type;
    EditText editTextsearchbarSpecific;
    List<GetEmployeeModel.employees> getEmployeeModels = new ArrayList<>();
    ApiService apiService;
    String empId = "";
    String empName = "";
    CustomProgressBar customProgressBar;

    private void initUI() {

        editTextsearchbarSpecific = findViewById(R.id.editTextsearchbarSpecific);
        submitButton = findViewById(R.id.submitButton);
        recycler_view = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_specific_recipient);

        apiService = ApiUtils.getAPIService();
        customProgressBar = new CustomProgressBar(this);
//        hide actionbar
        getSupportActionBar().hide();
//      typecasting
        initUI();

//         Recyclerview Properties
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(recyclerAdapter);

//        get All employees
        getEmployees();
//        onClick events
        ClickListener();
    }

    private void ClickListener() {

        //        search perform on text change update list
        editTextsearchbarSpecific.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    recycler_view.setVisibility(View.GONE);
                } else {
                    recycler_view.setAdapter(recyclerAdapter);
                    recycler_view.setVisibility(View.VISIBLE);
                }
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        take to Notify activity and set signature msg
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkin_type = "Package Delivery";
                if (!empId.equals("")) {
                    getPackageDelivered(checkin_type, empId, "Yes", "Yes");
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

    @Override
    public void onResultListener(String RecipientName, String id) {
        empId = id;
        empName = RecipientName;
        editTextsearchbarSpecific.setText(empName);

    }

    void getEmployees() {
        customProgressBar.showProgress();
        apiService.getAllEmployees().enqueue(new Callback<GetEmployeeModel>() {
            @Override
            public void onResponse(Call<GetEmployeeModel> call, Response<GetEmployeeModel> response) {
                if (response.isSuccessful()) {
                    customProgressBar.hideProgress();
                    getEmployeeModels = response.body().getEmployees();
                    for (int i = 0; i < getEmployeeModels.size(); i++) {
                        Log.e("Employee DATA", "onResponse: " + getEmployeeModels.get(i).getId());
                        recyclerAdapter = new RecyclerAdapter(SpecificRecipientActivity.this, getEmployeeModels, SpecificRecipientActivity.this);
//                        recyclerView.setAdapter(recyclerAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeModel> call, Throwable t) {

            }
        });
    }

    void getPackageDelivered(String CheckinType, String deliverToWhom, String isSignReq, String isSpecificPerson) {
        apiService.packageDelivery(CheckinType, deliverToWhom, isSignReq, isSpecificPerson).enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getType().equals("success")) {
                        Toast.makeText(SpecificRecipientActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SpecificRecipientActivity.this, NotifyActivity.class);
                        intent.putExtra("Specific", empName);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SpecificRecipientActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                Toast.makeText(SpecificRecipientActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        custom main bar layout
//        getMenuInflater().inflate(R.menu.menu_with_searchbar, menu);
//
////        search bar on actionbar
//        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
////          start search manager service
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
}
