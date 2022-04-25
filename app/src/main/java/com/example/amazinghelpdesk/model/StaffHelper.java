package com.example.amazinghelpdesk.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.util.ArrayList;

public class StaffHelper {

    private Context ctx;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public StaffHelper(Context context){
        this.ctx = context;
    }

    public void open() throws SQLException{
        helper = new DatabaseHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public ArrayList<Staff> viewStaffs(){
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        String query = "SELECT * FROM MsStaff";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Staff temp;
        String name, id, email, phone;
        String availableStatus;
        String availableStart, availableEnd;

        if(cursor.getCount() > 0){
            do{
                name = cursor.getString(cursor.getColumnIndexOrThrow("StaffName"));
                availableStatus = cursor.getString(cursor.getColumnIndexOrThrow("StaffAvailableStatus"));
                id = cursor.getString(cursor.getColumnIndexOrThrow("StaffId"));
                email = cursor.getString(cursor.getColumnIndexOrThrow("StaffEmail"));
                phone = cursor.getString(cursor.getColumnIndexOrThrow("StaffPhone"));
                availableStart = cursor.getString(cursor.getColumnIndexOrThrow("StaffStartTime"));
                availableEnd = cursor.getString(cursor.getColumnIndexOrThrow("StaffEndTime"));

                boolean availableStatusTemp = Boolean.parseBoolean(availableStatus);
                Time startTime = Time.valueOf(availableStart);
                Time endTime = Time.valueOf(availableEnd);

                temp = new Staff(name, availableStatusTemp, id, email, phone, startTime, endTime);
                staffArrayList.add(temp);
                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return staffArrayList;
    }

    public Staff findStaffById(String id){
        String query = "SELECT * FROM MsStaff WHERE StaffId = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Staff staff = null;
        String name, email, phone;
        String availableStatus;
        String availableStart, availableEnd;

        if(cursor != null){
            do{
                name = cursor.getString(cursor.getColumnIndexOrThrow("StaffName"));
                availableStatus = cursor.getString(cursor.getColumnIndexOrThrow("StaffAvailableStatus"));
                id = cursor.getString(cursor.getColumnIndexOrThrow("StaffId"));
                email = cursor.getString(cursor.getColumnIndexOrThrow("StaffEmail"));
                phone = cursor.getString(cursor.getColumnIndexOrThrow("StaffPhone"));
                availableStart = cursor.getString(cursor.getColumnIndexOrThrow("StaffStartTime"));
                availableEnd = cursor.getString(cursor.getColumnIndexOrThrow("StaffEndTime"));

                boolean availableStatusTemp = Boolean.parseBoolean(availableStatus);
                Time startTime = Time.valueOf(availableStart);
                Time endTime = Time.valueOf(availableEnd);

                staff = new Staff(name, availableStatusTemp, id, email, phone, startTime, endTime);

            }while(cursor.moveToNext());
        }
        return staff;
    }


}
