package com.example.wyshin.calander;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ShowScheduleDetail extends AppCompatActivity {
    private String[] schedule_array = new String[32];
    private String[][] time = new String[32][2];
    private ListView mListView;
    private int day;
    private String temp;
    private int get_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule_detail);

        //intent함수를 이용하여 key값을 이용해 원하는 값을 받는다.
        Intent intent = getIntent();

        get_day = intent.getExtras().getInt("day");

        mListView = (ListView)findViewById(R.id.listView);

        /* 아이템 추가 및 어댑터 등록 */
        dataSetting();
    }
    private void dataSetting() {
        MyAdapter mMyAdapter = new MyAdapter();

        try {
            FileInputStream fis = openFileInput("data9.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String str;
            while (true) {
                temp = br.readLine();
                if (temp != null) {
                    StringTokenizer st = new StringTokenizer(temp);
                    day = Integer.parseInt(st.nextToken("/"));
                    String schedule = st.nextToken("/");
                    schedule_array[day] = schedule;
                    time[day][0] = st.nextToken("/");
                    time[day][1] = st.nextToken("/");
                } else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clock), get_day, schedule_array[get_day],time[day][0],time[day][1]);


        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }
}

