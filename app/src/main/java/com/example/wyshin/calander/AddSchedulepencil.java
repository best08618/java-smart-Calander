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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class AddSchedulepencil extends AppCompatActivity {
    //Dialog함수에서 picker를 선택하기 위한 변수
    final int DIALOG_DATE = 1;
    final int DIALOG_TIME = 2;

    //xml과 연결시키기 위한 변수
    private TextView mDate;
    private Button date;
    private TextView mTime;
    private Button time;
    private EditText edit;
    private Button okay;
    private TextView mAlarm;
    private Button alarm;

    //datepicker로 부터 값을 받는 변수
    private int year_;
    private int month_;
    private int day_;
    //timepicker로 부터 값을 받는 변수
    private int hour_time;//시간 설정을 위한 변수
    private int min_time;
    private int hour_alarm;//알람 시간 설정을 위한 변수
    private int min_alarm;

    /*timepicker로 부터 어떤 것에 대한 시간인지 판별 하기 위한 변수
    스케줄 시간 -> time_=1
    알람 시간 -> time_=0 */
    private int time_ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedulepencil);

        //xml과 연결하여 사용자가 설정 값을 print
        mDate = (TextView) findViewById(R.id.showdate);
        mTime = (TextView) findViewById(R.id.showtime);
        date = (Button) findViewById(R.id.pickdate);
        time = (Button) findViewById(R.id.picktime);
        edit = (EditText) findViewById(R.id.schedule);
        okay = (Button) findViewById(R.id.submit);
        mAlarm = (TextView) findViewById(R.id.textalarm);
        alarm = (Button) findViewById(R.id.alarm);

        //date,time과 관련된 button event설정
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
        //okay버튼 클릭시 받은 정보를 파일에 저장하고 MainActivity 로 전환
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = (day_) + "/" + edit.getText().toString() + "/" + hour_time + "/" + min_time + "\n";//저장 할 정보를 string화 시킴
                try {
                    FileOutputStream fos = openFileOutput("data0.txt", Context.MODE_APPEND);
                    PrintWriter pw = new PrintWriter(fos);
                    pw.print(data);//printwriter를 이용하여 정보를 한줄로 저장
                    pw.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //toast로 파일에 저장한 값을 띄워줌
                Toast.makeText(AddSchedulepencil.this, "add schedule" + data, Toast.LENGTH_LONG).show();
                //MainActivity로 전환
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //시간과 날짜를 변수에 저장
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

    //id 값을 가지고 picker결정
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DATE:
                return new DatePickerDialog(this, mDateSetListener, year_, month_, day_);
            case DIALOG_TIME:
                if (time_ == 1)
                    return new TimePickerDialog(this, mTimeSetListener, hour_time, min_time, false);
                else
                    return new TimePickerDialog(this, mTimeSetListener, hour_alarm, min_alarm, false);
        }
        return null;
    }

    //picker로 고른 정보를 띄워주는 함수 -> 각각의 picker에 대해 정의함
    private void display_date() {
        mDate.setText(new StringBuilder().append(year_).append(" / ").append(month_ + 1).append(" / ").append(day_));
    }

    private void display_time() {
        mTime.setText(new StringBuilder().append(hour_time).append("시  ").append(min_time).append("분"));
    }

    private void display_alarm() {
        mAlarm.setText(new StringBuilder().append(hour_alarm).append("시  ").append(min_alarm).append("분"));
    }

    //Datepicker로 부터 받은 값을 저장 후 띄움
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_ = year;
            month_ = month;
            day_ = dayOfMonth;
            display_date();
        }
    };
    //timepicker로 받은 값을 저장 후 띄움
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //스케줄 시간
            if (time_ == 1) {
                hour_time = hourOfDay;
                min_time = minute;
                display_time();
            }
            //알람 시간
            else if (time_ == 0) {
                hour_alarm = hourOfDay;
                min_alarm = minute;
                display_alarm();
            }
        }
    };
}
