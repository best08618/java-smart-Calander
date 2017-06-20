package com.example.wyshin.calander;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    //스케줄을 보여주기 위하여 textview 정의
    private TextView show1;
    //private TextView show2;
    private TextView show3;
    //private TextView show4;
    //private TextView show5;
    private TextView show6;
    /*private TextView show7;
    private TextView show8;
    private TextView show9;
    private TextView show10;
    private TextView show11;
    private TextView show12;
    private TextView show13;
    private TextView show14;*/
    private TextView show15;
    /*private TextView show16;
    private TextView show17;
    private TextView show18;
    private TextView show19;
    private TextView show20;*/
    private TextView show21;
    /*private TextView show22;
    private TextView show23;
    private TextView show24;
    private TextView show25;
    private TextView show26;
    private TextView show27;
    private TextView show28;
    private TextView show29;*/
    private TextView show30;
    //세부 스케줄을 보여주기 위해 button 정의
    //private Button day1;
    //private Button day2;
    private Button day3;
    /*private Button day4;
    private Button day5;*/
    private Button day6;
    /*private Button day7;
    private Button day8;
    private Button day9;
    private Button day10;
    private Button day11;
    private Button day12;
    private Button day13;
    private Button day14;*/
    private Button day15;
    /*private Button day16;
    private Button day17;
    private Button day18;
    private Button day19;
    private Button day20;*/
    private Button day21;
    /*private Button day22;
    private Button day23;
    private Button day24;
    private Button day25;
    private Button day26;
    private Button day27;
    private Button day28;
    private Button day29;*/
    private Button day30;

    private Button monthly;

    private String[] data = new String[32];
    private int day;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show3 = (TextView) findViewById(R.id.show3);
        show6 = (TextView) findViewById(R.id.show6);
        show15 = (TextView) findViewById(R.id.show15);
        show21 = (TextView) findViewById(R.id.show21);
        show30 = (TextView) findViewById(R.id.show30);
        show1 = (TextView)findViewById(R.id.show5);
        day3 = (Button) findViewById(R.id.day07);
        day6 = (Button) findViewById(R.id.day13);
        day15 = (Button) findViewById(R.id.day25);
        day21 = (Button) findViewById(R.id.day34);
        day30 = (Button) findViewById(R.id.day46);

        monthly = (Button) findViewById(R.id.main);

        getdata();
        show();

        //툴바 추가 코드
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌

        //툴바 아이콘 바꾸기
        Drawable plus = getResources().getDrawable(R.drawable.plus);
        Bitmap bitmap = ((BitmapDrawable) plus).getBitmap();
        Drawable drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        toolbar.setOverflowIcon(drawable);

        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day3.setTextColor(getResources().getColor(R.color.Title));
                getdata();
                show();
                Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);
                intent.putExtra("day", 3);
                startActivity(intent);
            }
        });
        day6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day6.setTextColor(getResources().getColor(R.color.Title));
                getdata();
                show();
                Intent intent = new Intent(getApplicationContext(),ShowScheduleDetail.class);
                intent.putExtra("day",6);
                startActivity(intent);
            }
        });
        day15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day15.setTextColor(getResources().getColor(R.color.Title));
                getdata();
                show();
                Intent intent = new Intent(getApplicationContext(),ShowScheduleDetail.class);
                intent.putExtra("day",15);
                startActivity(intent);
            }
        });
        day21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day21.setTextColor(getResources().getColor(R.color.Title));
                getdata();
                show();
                Intent intent = new Intent(getApplicationContext(),ShowScheduleDetail.class);
                intent.putExtra("day",21);
                startActivity(intent);
            }
        });
        day30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day30.setTextColor(getResources().getColor(R.color.Title));
                getdata();
                show();
                Intent intent = new Intent(getApplicationContext(),ShowScheduleDetail.class);
                intent.putExtra("day",30);
                startActivity(intent);
            }
        });
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                show();
            }
        });
    }

    public void show() {
        show3.setText(data[3]);
        show6.setText(data[6]);
        show15.setText(data[15]);
        show21.setText(data[21]);
        show30.setText(data[30]);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //툴바의 menu 클릭시 추가 페이지 로 넘어가기
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.pencil) {
            Intent intent = new Intent(getApplicationContext(), AddSchedulepencil.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), AddSchedulePicture.class);
            startActivity(intent);
        }
        return true;
    }

    public void getdata() {
        //file 읽어오기
        try {
            FileInputStream fis = openFileInput("data9.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            while (true) {
                temp = br.readLine();
                if (temp != null) {
                    StringTokenizer st = new StringTokenizer(temp);
                    day = Integer.parseInt(st.nextToken("/"));
                    String schedule = st.nextToken("/");
                    data[day] = schedule;
                } else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getData() {
        return data;
    }
}