package com.example.amazinghelpdesk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.amazinghelpdesk.model.Reservation;
import com.example.amazinghelpdesk.model.ReservationHelper;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReservationViewActivity extends AppCompatActivity {
    private TextView tvStaffName, reservationDate, reservationTime, reservationName, reservationEmpty;
    private ReservationHelper helper;
    private ArrayList<Reservation> reservationArrayList = new ArrayList<>();
    private GridLayout reservationInfo;
    private LinearLayout linearDate, linearTime, linearName;

    private DrawerLayout staffDetailDWLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_view);
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

        toggle = new ActionBarDrawerToggle(ReservationViewActivity.this, staffDetailDWLayout, R.string.open_menu, R.string.close_menu);
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
                    intent = new Intent(ReservationViewActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuSetting){
                    intent = new Intent(ReservationViewActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuFaq){
                    intent = new Intent(ReservationViewActivity.this, FaqActivity.class);
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
        tvStaffName = findViewById(R.id.tv_offlineNameReservationView);
        reservationDate = findViewById(R.id.tv_reservationDate);
        reservationTime = findViewById(R.id.tv_reservationTime);
        reservationName = findViewById(R.id.tv_reservationName);
        reservationEmpty = findViewById(R.id.tv_reservationEmpty);

        //Layout
        reservationInfo = findViewById(R.id.grid_reservationInfo);
        linearDate = findViewById(R.id.linear_date);
        linearName = findViewById(R.id.linear_name);
        linearTime = findViewById(R.id.linear_time);

        String staffId = getIntent().getStringExtra("staffId");
        String staffName = getIntent().getStringExtra("staffName");

        helper = new ReservationHelper(ReservationViewActivity.this);
        helper.open();
        reservationArrayList = helper.viewReservationsById(staffId);
        helper.close();

        tvStaffName.setText(staffName);

        if(reservationArrayList.size() > 0){
            System.out.println("Reservation List Size with this Id: " + reservationArrayList.size());

            Reservation reservation = reservationArrayList.get(0);
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String newDate = newDateFormat.format(reservation.getDate());
            String newTime = convertTime(reservation.getTime().toString().substring(0, 5));

            reservationDate.setText(newDate);
            reservationTime.setText(newTime);
            reservationName.setText(reservation.getCustomerName());
        }else{
            //Remove all current linear layout
            linearTime.setVisibility(View.GONE);
            linearName.setVisibility(View.GONE);
            linearDate.setVisibility(View.GONE);

            //Show empty reservation message
            reservationEmpty.setVisibility(View.VISIBLE);
            reservationInfo.setRowCount(1);
        }




    }
}
