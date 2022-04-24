package com.example.amazinghelpdesk;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.amazinghelpdesk.model.Reservation;
import com.example.amazinghelpdesk.model.ReservationHelper;
import com.example.amazinghelpdesk.model.StaffHelper;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ReservationFormActivity extends AppCompatActivity {
    private TextView tvStaffName;
    private EditText date, time, name;
    private Button btnSave, btnView;
    private ReservationHelper helper;
    private ArrayList<Reservation> reservationArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.purple_500)));

        init();
    }

    private boolean validateDate(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean isValid = false;

        dateFormat.setLenient(false);
        Date inputDate = dateFormat.parse(date, new ParsePosition(0));
        Date currentDate = new Date();

        System.out.println(inputDate);

        if(inputDate != null){
            System.out.println(currentDate);
            System.out.println("Is it today" + inputDate.equals(currentDate));
            System.out.println(currentDate.compareTo(inputDate));
        }

        if(inputDate == null) {
            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_date_format), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            isValid = true;
        }

        return isValid;

    }

    private boolean validateTime(String date, String time, String startTime, String endTime){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        boolean isValid = false;

        String dateTime = date + " " + time;

        dateTimeFormat.setLenient(false);
        Date inputDateTime = dateTimeFormat.parse(dateTime, new ParsePosition(0));
        Date currentDateTime = new Date();

        String currDate = dateTimeFormat.format(currentDateTime);

        String startAvailable = date.substring(0, 10) + " " + startTime.substring(0, 5);
        String endAvailable = date.substring(0, 10)  + " " + endTime.substring(0, 5);

        Date currentStartAvailable = dateTimeFormat.parse(startAvailable, new ParsePosition(0));
        Date currentEndAvailable = dateTimeFormat.parse(endAvailable, new ParsePosition(0));

//        System.out.println("DateTimeStr: " + dateTime);
//        System.out.println("InputDateTime: " + inputDateTime);
//        System.out.println("startAvail: " + startAvailable);
//        System.out.println("endAvail: " + endAvailable);
//
        System.out.println("startAvail: " + currentStartAvailable);
        System.out.println("endAvail: " + currentEndAvailable);

        if(inputDateTime == null){
            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_time_format), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(inputDateTime.before(currentDateTime)){
            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_date_time_compare), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else if(inputDateTime.before(currentStartAvailable) || inputDateTime.after(currentEndAvailable)){
            String message = getResources().getText(R.string.error_time_reservation) + " " + startTime.substring(0, 5) + " and " + endTime.substring(0, 5);
            Toast.makeText(ReservationFormActivity.this, message, Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
            Date today = new Date();
            String todayStr = dateFormat.format(today);

            isValid = true;
        }

        return isValid;
    }

    private boolean validateName(String name){
        boolean isValid = false;

        if(name.length() < 3 || name.length() > 25){
            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_name_length), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            isValid = true;
        }

        return isValid;
    }

    private void init(){
        date = findViewById(R.id.et_reservationDate);
        time = findViewById(R.id.et_reservationTime);
        name = findViewById(R.id.et_reservationName);
        tvStaffName = findViewById(R.id.tv_offlineNameReservationForm);

        btnSave = findViewById(R.id.btn_saveReservation);
        btnView = findViewById(R.id.btn_viewReservation);

        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        String staffId = getIntent().getStringExtra("staffId");
        String staffName = getIntent().getStringExtra("staffName");

        tvStaffName.setText(staffName);

        //Initiate reservationList for current staff
        helper = new ReservationHelper(ReservationFormActivity.this);
        helper.open();
        reservationArrayList = helper.viewReservationsById(staffId);
        helper.close();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateStr = date.getText().toString();
                String timeStr = time.getText().toString();
                String nameStr = name.getText().toString();

                if(dateStr.isEmpty() || timeStr.isEmpty() || nameStr.isEmpty()){
                    Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_fill_reservation), Toast.LENGTH_SHORT).show();
                }else{
                    //Validation
                    if(validateDate(dateStr) && validateTime(dateStr, timeStr, startTime, endTime) && validateName(nameStr)){

                        helper = new ReservationHelper(ReservationFormActivity.this);
                        helper.open();

                        //CreateId automatically
                        if(reservationArrayList.size() > 0){
                            helper.updateReservation(staffId, dateStr, timeStr, nameStr);
                        }else{
                            String newReservationId = "RSV" + String.format("%03d", reservationArrayList.size()+1);
                            helper.insertReservation(staffId, newReservationId, dateStr, timeStr, nameStr);
                        }
                        helper.close();
                        Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.success_reservation), Toast.LENGTH_SHORT).show();
                        date.setText("");
                        name.setText("");
                        time.setText("");
                    }

                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservationFormActivity.this, ReservationViewActivity.class);
                intent.putExtra("staffName", staffName);
                intent.putExtra("staffId", staffId);
                startActivity(intent);

            }
        });

    }

}
