package com.example.wyshin.calander;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AddSchedulepencil extends AppCompatActivity {
    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;

    private TextView mDate;
    private Button date;
    private TextView mTime;
    private Button time;
    private EditText edit;
    private Button okay;
    private TextView mAlarm;
    private Button alarm;

    private int year_;
    private int month_;
    private int day_;
    private int hour_time;
    private int min_time;
    private int hour_alarm;
    private int min_alarm;

    private int time_=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedulepencil);
    //xml과 연결하여 사용자가 설정 값을 print
        mDate = (TextView) findViewById(R.id.showdate);
        mTime = (TextView) findViewById(R.id.showtime);
        date = (Button) findViewById(R.id.pickdate);
        time = (Button) findViewById(R.id.picktime);
        edit = (EditText)findViewById(R.id.schedule);
        okay = (Button)findViewById(R.id.submit);
        mAlarm = (TextView)findViewById(R.id.textalarm) ;
        alarm = (Button)findViewById(R.id.alarm);
    //date,time과 관련된 button이 눌리면 설정값을 보여준다
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            @Deprecated
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
                time_ = 1;
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
                time_ = 0;
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data =  (day_)+ "/"+edit.getText().toString()+"/"+hour_time+"/"+min_time+"\n";
                try{
                    FileOutputStream fos = openFileOutput("data0.txt", Context.MODE_APPEND);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.print(data);
                    pw.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(AddSchedulepencil.this,"add schedule"+data,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    //현재 시간과 날짜를 변수에 저장
        final Calendar c = Calendar.getInstance();
        year_ = c.get(Calendar.YEAR);
        month_ = c.get(Calendar.MONTH);
        day_ = c.get(Calendar.DAY_OF_MONTH);
        hour_time = c.get(Calendar.HOUR_OF_DAY);
        min_time = c.get(Calendar.MINUTE);
        hour_alarm = c.get(Calendar.HOUR_OF_DAY);
        min_alarm = c.get(Calendar.MINUTE);
    //사용자가 설정하기 전 처음 시간과 날짜를 print
        display_date();
        display_time();
        //display_alarm();

    }

    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DIALOG_DATE:
                return new DatePickerDialog(this,mDateSetListener,year_,month_,day_);
            case DIALOG_TIME:
                if(time_ == 1)
                    return new TimePickerDialog(this,mTimeSetListener,hour_time,min_time,false);
                else
                    return new TimePickerDialog(this,mTimeSetListener,hour_alarm,min_alarm,false);
        }
        return null;
    }
    private void display_date(){
        mDate.setText(new StringBuilder().append(year_).append(" / ").append(month_ + 1).append(" / ").append(day_));
    }
    private void display_time(){
        mTime.setText(new StringBuilder().append(hour_time).append("시  ").append(min_time).append("분"));
    }
    private void display_alarm(){
        mAlarm.setText(new StringBuilder().append(hour_alarm).append("시  ").append(min_alarm).append("분"));
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
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(time_ == 1) {
                hour_time = hourOfDay;
                min_time = minute;
                display_time();
            }
            else if(time_ == 0) {
                hour_alarm = hourOfDay;
                min_alarm = minute;
                display_alarm();
            }
        }
    };
}
