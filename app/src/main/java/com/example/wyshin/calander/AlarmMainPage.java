package com.example.wyshin.calander;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.Toast;

public class AlarmMainPage extends AppCompatActivity {
    Intent intent;       //<< For starting new Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main_page);
    // Initialize button here
        Button day= (Button) findViewById(R.id.datebutton);

        // add a onclick listener to button here
        day.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(AlarmMainPage.this,AlarmDayPicker.class);
                startActivity(intent);
            }
        });

        Button time = (Button)findViewById(R.id.timebutton);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AlarmMainPage.this,AlarmTimePicker.class);
                startActivity(intent);
            }
        });

        Button confirm= (Button) findViewById(R.id.confirmbutton);

        // add a onclick listener to button here
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(AlarmMainPage.this,AlarmMainPage.class);
                startActivity(intent);
                Toast.makeText(AlarmMainPage.this,"Reminder Set", Toast.LENGTH_LONG).show();
            }
        });
    }

}
