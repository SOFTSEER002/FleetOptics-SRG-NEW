package com.doozycod.fleetoptics.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.doozycod.fleetoptics.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.doozycod.fleetoptics.R.drawable.et_bg;

public class PersonalMeetingActivity extends AppCompatActivity {
    EditText visitorFullName, companyName, emailAddress, visitorPhoneNo;
    Button meetBackBtn, finishInterButton, submitInterButton;
    CheckBox checkBox, checkMultipleVisitor;
    LinearLayout companyBox, phoneBox, emailBox, linearLayout1st, linearLayout2nd;
    boolean isFinished = false;
    int numberOfLines = 0;
    ImageView AddPeopleBtn;
    List<EditText> allEds = new ArrayList<EditText>();
    ImageView tv;

    //    typecasting method
    private void initUI() {
//        edittext
        visitorFullName = findViewById(R.id.visitorFullName);
        AddPeopleBtn = findViewById(R.id.addPeople);
        companyName = findViewById(R.id.companyName);
        emailAddress = findViewById(R.id.emailAddress);
        visitorPhoneNo = findViewById(R.id.visitorPhoneNo);
        companyBox = findViewById(R.id.companyBox);
        phoneBox = findViewById(R.id.phoneBox);
        emailBox = findViewById(R.id.emailBox);


        linearLayout1st = findViewById(R.id.linearLayout1st);
        linearLayout2nd = findViewById(R.id.linearLayout2nd);
        /*
        linearLayout3rd = findViewById(R.id.linearLayout3rd);*/
        checkBox = findViewById(R.id.checkBox);
        checkMultipleVisitor = findViewById(R.id.checkMultipleVisitor);
        meetBackBtn = findViewById(R.id.meetBackBtn);
        submitInterButton = findViewById(R.id.submitInterButton);
        finishInterButton = findViewById(R.id.finishInterButton);
    }

    //      Dynamic Edit Text Method
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void AddDynamicEditText() {
        LinearLayout linearLayout2nd = (LinearLayout) findViewById(R.id.linearLayout2nd);
        linearLayout2nd.setVisibility(VISIBLE);
        linearLayout2nd.setWeightSum(1);
        linearLayout2nd.setPadding(0, 0, 0, 0);
        linearLayout2nd.setGravity(LinearLayout.VERTICAL);

        // add edittext
        EditText et = new EditText(this);
//        tv = new ImageView(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(1200, 150);
        p.setMargins(0, 0, 0, 30);

        et.setLayoutParams(p);

//        tv.setImageResource(R.drawable.ic_close);
        et.setHint("Visitor Name");
//        et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0);
        et.setPadding(40, 20, 20, 20);
        et.setBackground(getDrawable(et_bg));
        et.setId(numberOfLines + 1);

        allEds.add(et);

//        ll.addView(tv);
        linearLayout2nd.addView(et);
//        linearLayout2nd.addView(tv);
        numberOfLines++;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        set Activity as FULL SCREEN/hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee_personal);
//        hide Action bar
        getSupportActionBar().hide();


//        typecasting
        initUI();

//        change visibility for finish button and submit button
        finishInterButton.setEnabled(false);
        finishInterButton.setVisibility(View.GONE);
        submitInterButton.setEnabled(false);
        AddPeopleBtn.setVisibility(View.GONE);

//      click listener
        clickListeners();

    }

    public void removeLine(int i) {
        linearLayout2nd.removeViewAt(i);
    }

    public void removeLineAll() {
        linearLayout2nd.removeViewAt(linearLayout2nd.getChildCount() - 1);
    }

    private void clickListeners() {
        if (tv != null) {

        }

        AddPeopleBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                AddDynamicEditText();
            }
        });

//        visitor check if There is multiple visitors
        checkMultipleVisitor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if check for multiple people checked
                if (visitorFullName.getText().toString().equals("") || visitorPhoneNo.getText().toString().equals("")
                        || emailAddress.getText().toString().equals("")) {
                    checkMultipleVisitor.setChecked(false);
                    Toast.makeText(PersonalMeetingActivity.this, "Please fill all details than select!", Toast.LENGTH_SHORT).show();
                } else {
                    if (b) {
                        linearLayout1st.setVisibility(View.GONE);
                        linearLayout2nd.setVisibility(VISIBLE);
                        finishInterButton.setVisibility(VISIBLE);
                        finishInterButton.setEnabled(true);
                        AddDynamicEditText();
                        AddPeopleBtn.setVisibility(VISIBLE);
                    } else {
                        finishInterButton.setEnabled(true);
                        finishInterButton.setVisibility(View.GONE);
                        linearLayout1st.setVisibility(VISIBLE);
                        linearLayout2nd.setVisibility(View.GONE);
                        AddPeopleBtn.setVisibility(View.GONE);
                        numberOfLines = 0;

                        for (int i = 0; i < allEds.size(); i++) {
                            removeLineAll();
                        }
//                        clear edittext data is check Multiple is unChecked!
                        if (allEds.size() > 0) {
                            allEds.clear();
                        }
                    }
                }


            }
        });

//          I agree check box
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    submitInterButton.setEnabled(true);

                } else {
                    submitInterButton.setEnabled(false);
                }
            }
        });

//          on back press finish activity
        meetBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        finish button click set finish visiblity gone
        finishInterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPeopleBtn.setVisibility(View.GONE);
                Toast.makeText(PersonalMeetingActivity.this, "Contacts Added!", Toast.LENGTH_SHORT).show();
                finishInterButton.setVisibility(View.GONE);
                isFinished = true;
//                add dynamic editext data into Arraylist
                String[] strings = new String[allEds.size()];
                for (int i = 0; i < allEds.size(); i++) {
                    strings[i] = allEds.get(i).getText().toString();
                    Log.e("onClick!", "onClick: " + strings[i]);
                }

            }
        });
//        submit go to notify activity!
        submitInterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFinished) {
                    Intent intent = new Intent(PersonalMeetingActivity.this, NotifyActivity.class);
                    intent.putExtra("multiple", "multiple");
                    startActivity(intent);
                } else {
                    Toast.makeText(PersonalMeetingActivity.this, "Please tap finish first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
