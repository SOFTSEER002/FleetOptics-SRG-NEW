package com.doozycod.fleetoptics.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.Model.AppointmentResultModel;
import com.doozycod.fleetoptics.R;
import com.doozycod.fleetoptics.Service.ApiService;
import com.doozycod.fleetoptics.Service.ApiUtils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyActivity extends AppCompatActivity {
    TextView message, message2;
    ApiService apiService;

    //    typecasting method
    private void initUI() {
        message = findViewById(R.id.message);
        message2 = findViewById(R.id.message2);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notify);

//        hide actionbar
        getSupportActionBar().hide();
//         typecasting
        initUI();

        apiService = ApiUtils.getAPIService();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
//        if user is returning from signature Activity


        if (getIntent().hasExtra("appointment1")) {
            String encodedImage = Base64.encodeToString(convert(), Base64.DEFAULT);
            Log.e("encodedImage", "onCreate: " + encodedImage);
            message.setText("John has been notified and will be with you shortly… Thank You!");
            message2.setText("");

//            message2.setText("Sorry. John is not available at this time. Please try again later." +
//                    "Thank you for stopping by!");
            appointmentAPI(getIntent().getStringExtra("checkin"), getIntent().getStringExtra("purpose"), getIntent().getStringExtra("name"), getIntent().getStringExtra("co_name"),
                    getIntent().getStringExtra("emailID"), getIntent().getStringExtra("phone_no"), currentDateandTime, encodedImage, getIntent().getStringExtra("empId"));
//            return;
        }
        if (getIntent().hasExtra("interview")) {
            String encodedImage = Base64.encodeToString(convert(), Base64.DEFAULT);

            message.setText("Please wait…");
            message2.setText("");

//            message2.setText("Sorry. No one is available for your interview at this time. Your details have been submitted – HR will contact you as soon as possible.");
            appointmentAPI(getIntent().getStringExtra("checkin"), getIntent().getStringExtra("purpose"), getIntent().getStringExtra("name"), "",
                    getIntent().getStringExtra("emailID"), getIntent().getStringExtra("phone_no"), currentDateandTime, encodedImage, "2");
//            return;
        }
        if (getIntent().hasExtra("signature")) {
            message.setText("The recipient has been notified… Please wait.");
            message2.setText("Someone will be with you shortly to receive the package… Thank You!");
//            return;
        }
//        if user is returning from multiple visitor section

        if (getIntent().hasExtra("no_sign")) {
            message.setText("Please leave the package here, Thank You!");
            message2.setText("");
//            return;
        }
//        if visitor is delivering something
        else {
            message.setText("Please wait while we retrieve someone for you.");
            message2.setText("Someone will be with you shortly to receive the package… Thank You!");
        }

//        handler to return to home screen after ten seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(NotifyActivity.this, SplashActivity.class));
                finish();
            }
        }, 10000);
    }

    private byte[] convert() {
        Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/FleetOptics/" + "visitor.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        return baos.toByteArray();
    }

    @Override
    public void onBackPressed() {

       /* startActivity(new Intent(NotifyActivity.this, HomeActivity.class));
        finishAffinity();*/
    }

    void appointmentAPI(String checkinType, String purpose_of_visit, String fullname, String company_name, String email_address, String phone_no, String timestamp, String image, String empId) {

        Log.e("appointmentAPI", "Array: " + fullname + empId);
        apiService.appointment(checkinType, purpose_of_visit, fullname, company_name, email_address, phone_no, timestamp, image, empId).enqueue(new Callback<AppointmentResultModel>() {
            @Override
            public void onResponse(Call<AppointmentResultModel> call, Response<AppointmentResultModel> response) {
                if (response.isSuccessful()) {
                    Log.e("Response", "onResponse: " + response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<AppointmentResultModel> call, Throwable t) {
                Log.e("REsponse", "onResponse: " + t.getMessage());
            }
        });
    }

}
