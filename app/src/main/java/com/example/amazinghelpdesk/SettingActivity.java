package com.example.amazinghelpdesk;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SettingActivity extends AppCompatActivity {

    private DrawerLayout settingDWLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Button btnChangeTheme, btnUninstall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        if(settingDWLayout.isDrawerOpen(GravityCompat.START)){
            settingDWLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void menu(){
        settingDWLayout = findViewById(R.id.dwLayoutSetting);

        toggle = new ActionBarDrawerToggle(this, settingDWLayout, R.string.open_menu, R.string.close_menu);
        settingDWLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navViewSetting);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                settingDWLayout.closeDrawer(GravityCompat.START);

                Intent intent;

                if(id == R.id.menuHome){
                    intent = new Intent(SettingActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuFaq){
                    intent = new Intent(SettingActivity.this, FaqActivity.class);
                    startActivity(intent);
                }else{
                    return false;
                }
                return true;
            }
        });
    }

    private void init(){
        btnChangeTheme = findViewById(R.id.btn_changeTheme);
        btnUninstall = findViewById(R.id.btn_uninstall);

        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNightMode = SettingActivity.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

                if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

            }
        });

        btnUninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:com.example.amazinghelpdesk"));
                startActivity(intent);
            }
        });

    }
}
