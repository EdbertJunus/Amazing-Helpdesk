package com.example.amazinghelpdesk.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReservationHelper {
    private Context ctx;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public ReservationHelper(Context context){
        this.ctx = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(ctx);
        db = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public ArrayList<Reservation> viewReservationsById(String id){
        ArrayList<Reservation> reservationArrayList = new ArrayList<>();
        String query = "SELECT * FROM MsReservation WHERE StaffId = '" +id+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Reservation temp;
        String staffId, reservationId, reservationName;
        String date;
        String time;

        if(cursor.getCount() > 0){
            do{
                staffId = cursor.getString(cursor.getColumnIndexOrThrow("StaffId"));
                reservationId = cursor.getString(cursor.getColumnIndexOrThrow("ReservationId"));
                date = cursor.getString(cursor.getColumnIndexOrThrow("ReservationDate"));
                time = cursor.getString(cursor.getColumnIndexOrThrow("ReservationTime"));
                reservationName = cursor.getString(cursor.getColumnIndexOrThrow("ReservationName"));

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                ParsePosition position = new ParsePosition(0);
                Date reservationDate = formatter.parse(date, position);

                Time reservationTime = Time.valueOf(time+":00");

                temp = new Reservation(staffId, reservationId, reservationDate, reservationTime, reservationName);
                reservationArrayList.add(temp);
                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return reservationArrayList;
    }

    public void insertReservation(String staffId, String reservationId, String reservationDate, String reservationTime, String reservationName){
        String query = "INSERT INTO MsReservation VALUES ('" + staffId + "', '" + reservationId + "', '" + reservationDate + "', '" + reservationTime + "', '" + reservationName + "')";
        db.execSQL(query);
    }

    public void updateReservation(String staffId, String reservationDate, String reservationTime, String reservationName){

        String query = "UPDATE MsReservation SET ReservationDate = '" + reservationDate + "', ReservationTime = '" + reservationTime + "', ReservationName = '" + reservationName + "' WHERE StaffId = '" + staffId + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor != null){
            db.execSQL(query);
        }
    }

}
