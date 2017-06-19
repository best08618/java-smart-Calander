package com.example.wyshin.calander;

import android.content.ClipData;
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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private TextView show3;
    private TextView show6;
    private TextView show15;
    private TextView show21;
    private TextView show30;
    private Button day3;
    private Button monthly;
    private String[] data = new String[32];
    private int day;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show3 = (TextView)findViewById(R.id.show3);
        show6 = (TextView)findViewById(R.id.show6);
        show15 = (TextView)findViewById(R.id.show15);
        show21 = (TextView)findViewById(R.id.show21);
        show30 = (TextView)findViewById(R.id.show30);
        day3 = (Button)findViewById(R.id.day07);
        monthly = (Button)findViewById(R.id.main);
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
            /*day3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getdata();
                    Toast.makeText(MainActivity.this, temp, Toast.LENGTH_SHORT).show();
                }
            });*/
            day3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    day3.setTextColor(getResources().getColor(R.color.Title));
                    getdata();
                    show();
                    Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);
                    intent.putExtra("day",3);
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
    public void show(){
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
    public boolean onOptionsItemSelected (MenuItem item){
                if(item.getItemId() == R.id.pencil){
                    Intent intent = new Intent(getApplicationContext(), AddSchedulepencil.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), AddSchedulePicture.class);
                    startActivity(intent);
                }
        return true;
    }
    public void getdata(){
        //file 읽어오기
        try {
            FileInputStream fis = openFileInput("data9.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            while(true) {
                temp = br.readLine();
                if (temp != null) {
                    StringTokenizer st = new StringTokenizer(temp);
                    day = Integer.parseInt(st.nextToken("/"));
                    String schedule = st.nextToken("/");
                    data[day] = schedule;
                }
                else
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