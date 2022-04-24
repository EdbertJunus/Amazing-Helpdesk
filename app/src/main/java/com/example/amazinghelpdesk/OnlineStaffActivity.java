package com.example.amazinghelpdesk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.amazinghelpdesk.model.Staff;
import com.example.amazinghelpdesk.model.StaffHelper;

public class OnlineStaffActivity extends AppCompatActivity {
    private TextView name, email, phone;
    private Button btnEmail, btnCall;
    private StaffHelper helper;
    private Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_staff);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.purple_500)));

        init();
    }

    private void init(){
        String id = getIntent().getStringExtra("staffId");

        helper = new StaffHelper(this);
        helper.open();
        staff = helper.findStaffById(id);
        helper.close();

        name = findViewById(R.id.tv_onlineName);
        email = findViewById(R.id.tv_onlineEmail);
        phone = findViewById(R.id.tv_onlinePhone);

        btnEmail = findViewById(R.id.btn_email);
        btnCall = findViewById(R.id.btn_call);

        name.setText(staff.getName());
        email.setText("Email: " + staff.getEmail());

        int phoneLength = staff.getPhone().length();
        String newPhoneFormat = staff.getPhone().substring(1, phoneLength);
        phone.setText("Phone: +62" + newPhoneFormat);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("mailto: " + staff.getEmail()));
                startActivity(intent);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + "+62" + newPhoneFormat));
                startActivity(intent);
            }
        });

    }
}
