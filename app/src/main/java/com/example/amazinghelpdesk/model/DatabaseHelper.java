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

        query = "CREATE TABLE MsReservation (StaffId, ReservationId, ReservationDate, ReservationTime, ReservationName)";
        sqLiteDatabase.execSQL(query);

        query = "INSERT INTO MsStaff VALUES " +
                "('James Bond', 'true', 'BNS001', 'James_bond@gmail.com', '081245678956', '17:00:00', '23:00:00'), " +
                "('Harry Potter', 'true', 'BNS002', 'Harry_potter@gmail.com', '081212341236', '09:30:00', '20:00:00'), " +
                "('Mr Bean', 'true', 'BNS003', 'bean@gmail.com', '08121123456789', '09:00:00', '12:00:00'), " +
                "('Tony Stark', 'true', 'BNS004', 'tony@gmail.com', '081211128956', '18:00:00', '01:00:00'), " +
                "('Doraemon', 'false', 'BNS005', 'doreamon@gmail.com', '081288888231', '21:00:00', '05:00:00'), " +
                "('Captain America', 'true', 'BNS006', 'capt@gmail.com', '081161878888', '00:00:00', '23:00:00'), " +
                "('Goku', 'false', 'BNS007', 'goku@gmail.com', '081282318231', '15:00:00', '16:00:00'), " +
                "('Kabayan', 'false', 'BNS008', 'kabayan@gmail.com', '081161618899', '18:00:00', '20:00:00'), " +
                "('Sangkuriang', 'false', 'BNS009', 'sang@gmail.com', '081245678232', '17:00:00', '21:00:00'), " +
                "('Michael Jackson', 'false', 'BNS010', 'michaelJack@gmail.com', '081222438956', '08:00:00', '20:00:00'), " +
                "('Elvis Presley', 'false', 'BNS011', 'elvis@gmail.com', '081288118231', '17:00:00', '18:00:00')," +
                "('Zhao Yun', 'false', 'BNS012', 'Heroes@neverfail.com', '088899998888', '11:00:00', '18:00:00')";
        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS MsStaff";
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS MsReservation";
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }
}
