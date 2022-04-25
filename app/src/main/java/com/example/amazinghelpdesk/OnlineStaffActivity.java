package com.example.amazinghelpdesk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.amazinghelpdesk.model.Staff;
import com.example.amazinghelpdesk.model.StaffHelper;
import com.google.android.material.navigation.NavigationView;

public class OnlineStaffActivity extends AppCompatActivity {
    private TextView name, email, phone;
    private Button btnEmail, btnCall;
    private StaffHelper helper;
    private Staff staff;

    private DrawerLayout staffDetailDWLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_staff);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.purple_500)));

        menu();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(staffDetailDWLayout.isDrawerOpen(GravityCompat.START)){
            staffDetailDWLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void menu(){
        staffDetailDWLayout = findViewById(R.id.dwLayoutStaffDetail);

        toggle = new ActionBarDrawerToggle(OnlineStaffActivity.this, staffDetailDWLayout, R.string.open_menu, R.string.close_menu);
        staffDetailDWLayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navViewStaffDetail);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                staffDetailDWLayout.closeDrawer(GravityCompat.START);
                Intent intent;
                if(id == R.id.menuHome){
                    intent = new Intent(OnlineStaffActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuSetting){
                    intent = new Intent(OnlineStaffActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuFaq){
                    intent = new Intent(OnlineStaffActivity.this, FaqActivity.class);
                    startActivity(intent);
                }else{
                    return false;
                }
                return true;
            }
        });
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
