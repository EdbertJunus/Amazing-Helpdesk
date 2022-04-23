package com.example.amazinghelpdesk.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String name = "AmazingHelpdeskDB";
    private final static SQLiteDatabase.CursorFactory factory = null;
    private final static int version = 1;

    public DatabaseHelper(Context ctx){
        super(ctx, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE MsStaff (StaffName, StaffAvailableStatus, StaffId, StaffEmail, StaffPhone, StaffStartTime, StaffEndTime)";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO MsStaff VALUES " +
                "('Doraemon', 'true', 'BNS001', 'doraemon@gmail.com', '081245678956', '17:00:00', '23:00:00'), " +
                "('Shizuka', 'true', 'BNS002', 'shizuka@gmail.com', '081212348956', '18:00:00', '20:00:00'), " +
                "('Onepiece', 'false', 'BNS003', 'onepiece@gmail.com', '081245678231', '17:00:00', '21:00:00'), " +
                "('Zoro', 'true', 'BNS004', 'zoro@gmail.com', '081212348956', '18:00:00', '20:00:00'), " +
                "('Sanji', 'false', 'BNS005', 'sanji@gmail.com', '081245678231', '17:00:00', '21:00:00'), " +
                "('Luffy', 'true', 'BNS006', 'shizuka@gmail.com', '081212348956', '18:00:00', '20:00:00'), " +
                "('Blackbear', 'false', 'BNS007', 'onepiece@gmail.com', '081245678231', '17:00:00', '21:00:00'), " +
                "('Akanu', 'true', 'BNS008', 'shizuka@gmail.com', '081212348956', '18:00:00', '20:00:00'), " +
                "('Aokiji', 'false', 'BNS009', 'onepiece@gmail.com', '081245678231', '17:00:00', '21:00:00'), " +
                "('Itachi', 'true', 'BNS010', 'itachie@gmail.com', '081212348956', '18:00:00', '20:00:00'), " +
                "('Naruto', 'false', 'BNS011', 'onepiece@gmail.com', '081245678231', '17:00:00', '21:00:00')";
        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS MsStaff";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}
