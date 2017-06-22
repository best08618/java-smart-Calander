package com.example.wyshin.calander;

/*
*day버튼 클릭시 세부사항을 보여주는 class와 연결
* file을 기본으로 일정관련 정보를 받음
*이전의 MainActivity에서 보내준 값을 기준으로 day를 받음
* 해당day에 대한 세부정보를 띄워줌
*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ShowScheduleDetail extends AppCompatActivity {
    //file에서 가져온 값을 day에 따라 배열에 저장
    private String[] schedule_array = new String[32];//스케줄 저장 배열
    private String[][] time = new String[32][2];//시와 분을 저장해야 하므로 2차원배열
    private Bitmap[] img = new Bitmap[32];//사진 저장 배열

    private ListView mListView;//스캐줄을 띄우는 listview
    private int day;//날짜 저장 int
    private String temp;//한줄 읽어오기 위한 String
    private int get_day;//intent 클래스로 "day"key값으로 부터 받는 int

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule_detail);

        //intent 클래스를 이용하여 key값을 이용해 원하는 값을 받는다.
        Intent intent = getIntent();
        get_day = intent.getExtras().getInt("day");//day라는 key값 으로 버튼이 눌린 날짜를 받는다
        mListView = (ListView) findViewById(R.id.listView);//xml listview와 연결

        dataSetting();//어뎁터등록, 보여줄 정보 추가
    }

    //어뎁터 등록, 아이템 추가 함수
    private void dataSetting() {
        MyAdapter mMyAdapter = new MyAdapter();//MyAdapter클래스 인스턴스화

        try {
            FileInputStream fis = openFileInput("data0.txt");//바이트 스트림과 문제 스트림 연결 -> 문자로 변환
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));//버퍼에 저장되어 있는 것은 한꺼번에 받음
            while (true) {
                temp = br.readLine();//한 줄씩 읽음
                if (temp != null) {
                    StringTokenizer st = new StringTokenizer(temp);//temp를 tokenizing
                    //'/'를 기준으로 tokenizing -> 저장한 순서대로 정보를 받음
                    day = Integer.parseInt(st.nextToken("/"));
                    String schedule = st.nextToken("/");
                    //정보를 배열에 저장
                    schedule_array[day] = schedule;
                    time[day][0] = st.nextToken("/");
                    time[day][1] = st.nextToken("/");
                } else//파일의 끝을 읽었을 때 while문 빠져나감
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //받은 정보 배열에서 보여주어야 하는 갑만 보내줌
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clock), get_day, schedule_array[get_day], time[day][0], time[day][1]);


        //listview에 adapter 등록
        mListView.setAdapter(mMyAdapter);
    }
}

