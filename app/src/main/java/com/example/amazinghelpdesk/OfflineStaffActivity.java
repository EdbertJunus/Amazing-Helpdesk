package com.example.amazinghelpdesk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OfflineStaffActivity extends AppCompatActivity {
    private TextView time, name;
    private Button btnReserve;
    private StaffHelper helper;
    private Staff staff;

    private DrawerLayout staffDetailDWLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_staff);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.purple_500)));

        init();
        menu();
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

        toggle = new ActionBarDrawerToggle(OfflineStaffActivity.this, staffDetailDWLayout, R.string.open_menu, R.string.close_menu);
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
                    intent = new Intent(OfflineStaffActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuSetting){
                    intent = new Intent(OfflineStaffActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuFaq){
                    intent = new Intent(OfflineStaffActivity.this, FaqActivity.class);
                    startActivity(intent);
                }else{
                    return false;
                }
                return true;
            }
        });
    }


    public String convertTime(String time){
        String newTime = time;
        int hour = Integer.parseInt(time.substring(0, 2));
        if( hour > 12){
            int newHour = hour - 12;
            String frontZero = newHour > 9 ? "" : "0";
            newTime = frontZero + String.valueOf(newHour) + newTime.substring(2, newTime.length());
            newTime += " PM";
        }else{
            newTime += " AM";
        }
        return newTime;
    }

    private void init(){
        String id = getIntent().getStringExtra("staffId");

        helper = new StaffHelper(this);
        helper.open();
        staff = helper.findStaffById(id);
        helper.close();

        name = findViewById(R.id.tv_offlineName);
        time = findViewById(R.id.tv_offlineTime);
        btnReserve = findViewById(R.id.btn_reserve);

        Time startTime = staff.getAvailableStart();
        Time endTime = staff.getAvailableEnd();

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String startTimeString = timeFormat.format(startTime.getTime());
        String endTimeString = timeFormat.format(endTime.getTime());

        startTimeString = convertTime(startTimeString);
        endTimeString = convertTime(endTimeString);


        time.setText("Will be available at " + startTimeString + " until " + endTimeString);
        name.setText(staff.getName());

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OfflineStaffActivity.this, ReservationFormActivity.class);
                intent.putExtra("startTime", startTime.toString());
                intent.putExtra("endTime", endTime.toString());
                intent.putExtra("staffId", id);
                intent.putExtra("staffName", staff.getName());
                startActivity(intent);
            }
        });

    }
}
