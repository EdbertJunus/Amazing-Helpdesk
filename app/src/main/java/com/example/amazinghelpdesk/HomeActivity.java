package com.example.amazinghelpdesk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazinghelpdesk.adapter.StaffAdapter;
import com.example.amazinghelpdesk.model.Staff;
import com.example.amazinghelpdesk.model.StaffHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvAvailableStaffs;
    private RecyclerView rvOfflineStaffs;
    private DrawerLayout homeDWLayout;

    private StaffHelper helper;
    private ArrayList<Staff> staffArrayList;
    private ArrayList<Staff> onlineStaffs, offlineStaffs;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        if(homeDWLayout.isDrawerOpen(GravityCompat.START)){
            homeDWLayout.closeDrawer(GravityCompat.START);
        }else{
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void menu(){
        homeDWLayout = findViewById(R.id.dwLayoutHome);

        toggle = new ActionBarDrawerToggle(HomeActivity.this, homeDWLayout, R.string.open_menu, R.string.close_menu);
        homeDWLayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navViewHome);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                homeDWLayout.closeDrawer(GravityCompat.START);
                Intent intent;

                if(id == R.id.menuSetting){
                    intent = new Intent(HomeActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuFaq){
                    intent = new Intent(HomeActivity.this, FaqActivity.class);
                    startActivity(intent);
                }else{
                    return false;
                }
                return true;
            }
        });
    }

    private void splitStaff(){
        for (Staff s:staffArrayList) {
            if(s.isAvailableStatus()){
                onlineStaffs.add(s);
            }else{
                offlineStaffs.add(s);
            }
        }
        
    }

    private void init(){

        helper = new StaffHelper(this);

        helper.open();
        staffArrayList = helper.viewStaffs();
        helper.close();

        rvAvailableStaffs = findViewById(R.id.rv_availableStaffs);
        rvOfflineStaffs = findViewById(R.id.rv_offlineStaffs);

        onlineStaffs = new ArrayList<>();
        offlineStaffs = new ArrayList<>();

        splitStaff();
//        System.out.println("Size Online: "+ onlineStaffs.size());
//        System.out.println("Size Offline: "+ offlineStaffs.size());

        rvAvailableStaffs.setAdapter(new StaffAdapter(this, onlineStaffs));
        rvAvailableStaffs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvOfflineStaffs.setAdapter(new StaffAdapter(this, offlineStaffs));
        rvOfflineStaffs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

}
