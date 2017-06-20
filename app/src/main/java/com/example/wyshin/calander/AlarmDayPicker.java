package com.example.wyshin.calander;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class AlarmDayPicker extends AppCompatActivity {

    private int year_;
    private int month_;
    private int day_;

    final int DIALOG_DATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_day_picker);

        Button date_ok = (Button) findViewById(R.id.ok);
        date_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            @Deprecated
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });

        date_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AlarmDayPicker.this, AlarmMainPage.class);
                startActivity(intent);
            }
        });
    }

    private void display_date(){
        mDate.setText(new StringBuilder().append(year_).append(" / ").append(month_ + 1).append(" / ").append(day_));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_ = year;
            month_ = month;
            day_ = dayOfMonth;
            display_date();
        }
    };
}
