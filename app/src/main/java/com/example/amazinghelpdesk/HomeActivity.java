package com.example.amazinghelpdesk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazinghelpdesk.adapter.StaffAdapter;
import com.example.amazinghelpdesk.model.Staff;
import com.example.amazinghelpdesk.model.StaffHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvAvailableStaffs;
    private RecyclerView rvOfflineStaffs;

    private StaffHelper helper;
    private ArrayList<Staff> staffArrayList;
    private ArrayList<Staff> onlineStaffs, offlineStaffs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
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
        System.out.println("Size Online: "+ onlineStaffs.size());
        System.out.println("Size Offline: "+ offlineStaffs.size());

        rvAvailableStaffs.setAdapter(new StaffAdapter(this, onlineStaffs));
        rvAvailableStaffs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvOfflineStaffs.setAdapter(new StaffAdapter(this, offlineStaffs));
        rvOfflineStaffs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

}
