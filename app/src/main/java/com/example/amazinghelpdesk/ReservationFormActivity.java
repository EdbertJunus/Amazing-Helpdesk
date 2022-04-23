package com.example.amazinghelpdesk;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationFormActivity extends AppCompatActivity {
    private EditText date, time, name;
    private Button btnSave, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);
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
        }else if(inputDate.before(currentDate) || inputDate.equals(currentDate)){
            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_date_compare), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else{
            isValid = true;
        }

        return isValid;

    }

    private boolean validateTime(String date, String time){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        boolean isValid = false;

//        timeFormat.setLenient(false);
//        Time inputTime = (Time) timeFormat.parse(time, new ParsePosition(0));
//        Time currentTime = new Time(System.currentTimeMillis());

        System.out.println(time);

//        if(inputTime == null){
//            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_time_format), Toast.LENGTH_SHORT).show();
//            isValid = false;
//        }else if(inputTime.before(currentTime)){
//            Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_time_compare), Toast.LENGTH_SHORT).show();
//            isValid = false;
//        }

        return isValid;
    }

    private void init(){
        date = findViewById(R.id.et_reservationDate);
        time = findViewById(R.id.et_reservationTime);
        name = findViewById(R.id.et_reservationName);

        btnSave = findViewById(R.id.btn_saveReservation);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateStr = date.getText().toString();
                String timeStr = time.getText().toString();
                String nameStr = name.getText().toString();

                if(dateStr.isEmpty() || timeStr.isEmpty() || nameStr.isEmpty()){
                    Toast.makeText(ReservationFormActivity.this, getResources().getText(R.string.error_fill_reservation), Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //Validation
                    if(validateDate(dateStr) && validateTime(dateStr, timeStr)){
                        System.out.println("Date: " + dateStr);
                        System.out.println("Time: " + timeStr);
                        System.out.println("Name: " + nameStr);
                    }

                }
            }
        });

    }

}
