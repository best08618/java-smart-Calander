package com.example.wyshin.calander;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by samsung on 2017-06-08.
 */

public class D_day_main extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    /*xml 에 있는  요소들과 연결하기 위한 변수들이다.*/
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private TextView content1;
    private TextView content2;
    private TextView content3;
    private TextView content4;
    private TextView resultNum1;
    private TextView resultNum2;
    private TextView resultNum3;
    private TextView resultNum4;
    private Button monthly;//전체 월간달력 페이지로 연결
    private Button alarm;//알람 메인 페이지로 연결

    /* 파일에 저장된 값을 tokenizing한 후 각각을 저장할 변수들이다*/
    private String context;
    private String date;
    private String icon;
    /* 이후 ㅣayout의 색을 바꾸기 위해 layout을 설정할 변수이다*/
    private LinearLayout layout1;
    private LinearLayout layout3;
    /* 파일을 읽어을때 파일의 갯수에 따라 어디 textview에 저장할 지 등을 결정하기 위해 사용할 변수이다.*/
    private  int i,j;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_day_main); //d_day main xml과 연결하는 class이다.
        /* Toolbar에 플러스버튼을 추가하기 위해 따로 설정을 해주었다*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //제목의 칼라
        toolbar.setSubtitle("d_day"); //부제목 넣기
        toolbar.setNavigationIcon(R.drawable.icon); //제목앞에 아이콘 넣기
        setSupportActionBar(toolbar); //툴바를 액션바와 같게 만들어 준다

        j=0;

        try {
            /*파일로부터 값을 받아오기 위한 함수들이다.*/
            FileInputStream fis = openFileInput("data_input2.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            FileInputStream fos=  openFileInput("image_input2.txt");
            BufferedReader bi = new BufferedReader(new InputStreamReader(fos));
            while (true) {
                String temp = br.readLine();
                String imgTemp = bi.readLine();
                Bitmap bp=StringToBitMap(imgTemp); //읽은 string을 bitmap으로 다시 보낸다.
                if (temp != null) {
                    j++;
                    i=j%4; // 4개이상인경우 1번부터 다시 덮어쓰기 위해서 모듈러를 사용해준다.
                    StringTokenizer st = new StringTokenizer(temp); //토크나이징을 통해 토크나이징을 해준다.
                    context = st.nextToken();
                    date = st.nextToken();
                    icon=st.nextToken();
                    if(i==1) { //i 가 1인경우
                        //1번정보가 들어갈 xml 부분을 연결해준다.
                        layout1=(LinearLayout)findViewById(R.id.layout1);
                        content1 = (TextView) findViewById(R.id.content1);
                        resultNum1 = (TextView) findViewById(R.id.date1);
                        image1 = (ImageView) findViewById(R.id.image1);
                        if(!(icon.equals("/"))) // icon이 /일경우는 사진을 선택한 경우이므로 두경우를 if else 로 구분해준다.
                            chooseIcon(icon,image1);
                        else
                            chooseImage(bp,image1);
                        content1.setText(String.format("%s", context));
                        resultNum1.setText(String.format("%s", date));
                        layout1.setBackgroundColor(Color.argb(100,247,170,151)); // layout 색을 바꿔준다.
                    }
                    if(i==2) {
                        content2 = (TextView) findViewById(R.id.content2);
                        resultNum2 = (TextView) findViewById(R.id.date2);
                        image2 = (ImageView) findViewById(R.id.image2);
                        if((!icon.equals("/")))
                            chooseIcon(icon,image2);
                        else
                            chooseImage(bp,image2);
                        content2.setText(String.format("%s", context));
                        resultNum2.setText(String.format("%s", date));
                    }
                    if(i==3) {
                        layout3=(LinearLayout)findViewById(R.id.layout3);
                        content3 = (TextView) findViewById(R.id.content3);
                        resultNum3 = (TextView) findViewById(R.id.date3);
                        image3 = (ImageView) findViewById(R.id.image3);
                        if(!(icon.equals("/")))
                            chooseIcon(icon,image3);
                        else
                            chooseImage(bp,image3);
                        content3.setText(String.format("%s", context));
                        resultNum3.setText(String.format("%s", date));
                        layout3.setBackgroundColor(Color.argb(100,247,170,151));
                    }
                    if(i==0) {
                        content4 = (TextView) findViewById(R.id.content4);
                        resultNum4 = (TextView) findViewById(R.id.date4);
                        image4 = (ImageView) findViewById(R.id.image4);
                        if(!(icon.equals("/")))
                            chooseIcon(icon,image4);
                        else
                            chooseImage(bp,image4);
                        content4.setText(String.format("%s", context));
                        resultNum4.setText(String.format("%s", date));
                    }
                }
                else
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(D_day_main.this, "catch_in main",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        monthly = (Button) findViewById(R.id.monthly);
        alarm = (Button) findViewById(R.id.alarm);//click시 알람 main페이지를 띄워줌

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        /*//알람button click에 대한 event작성
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AlarmMainPage.class);
                startActivity(intent);
            }
        });*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }
    /* add 버튼을 눌렀을경우 추가하는 activity로 이동한다*/
    public void add(View v){
        Intent sbm = new Intent(this, D_day.class);
        startActivity(sbm);
    }

    public void chooseImage(Bitmap bp, ImageView image) //image를 선택한 경우 image를 자리에 넣어준다.
    {
        image.setImageBitmap(bp);
    }
    public void chooseIcon(String icon,ImageView image){ //icon을 선택한 경우 icon string을 비교하여 그에 맞는 사진을 띄어준다.
        if(icon.equals("love"))
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.love));
        }
        if(icon.equals("birthday"))
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.birthday));
        }
        if(icon.equals("star"))
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.star));
        }
        if(icon.equals("exam"))
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.evaluation));
        }
        if(icon.equals("airplane"))
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.airplane));
        }
        if(icon.equals("ring"))
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.ring));
        }
    }
    public Bitmap StringToBitMap(String encodedString){ //파일에 있는 String 값을 bitmap으로 바꿔주는 함수이다.
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}


