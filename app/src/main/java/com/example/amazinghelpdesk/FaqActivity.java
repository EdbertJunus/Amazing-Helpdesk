package com.example.amazinghelpdesk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class FaqActivity extends AppCompatActivity {

    private DrawerLayout faqDWLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.purple_500)));

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
        if(faqDWLayout.isDrawerOpen(GravityCompat.START)){
            faqDWLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void menu(){
        faqDWLayout = findViewById(R.id.dwLayoutFaq);

        toggle = new ActionBarDrawerToggle(FaqActivity.this, faqDWLayout, R.string.open_menu, R.string.close_menu);
        faqDWLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navViewFaq);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                faqDWLayout.closeDrawer(GravityCompat.START);

                Intent intent;

                if(id == R.id.menuHome){
                    intent = new Intent(FaqActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menuSetting){
                    intent = new Intent(FaqActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else{
                    return false;
                }
                return true;
            }
        });
    }

}
