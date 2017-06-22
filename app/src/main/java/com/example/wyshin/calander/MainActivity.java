package com.example.wyshin.calander;

/*activity_main 과 연결
* 월간달력에서 일정을 보여주는 역할
* 파일에 저장된 일정을 tokenizer을 통해 추출
* 일을 누르면 해당 날짜의 일정 세부내용을 보여줌
* 아래의 메뉴 버튼을 누르면 각 메뉴에 써있는 페이지로 이동
* */

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
    private TextView show3;
    private TextView show6;
    private TextView show15;
    private TextView show21;
    private TextView show30;

    //해당 날짜의 세부 스케줄을 보여주기 위해 button 정의
    private Button day3;
    private Button day6;
    private Button day15;
    private Button day21;
    private Button day30;

    //activity-main에서 각 해당하는 xml을 보여 주기 위해 button정의
    private Button monthly;//전체 월간달력 페이지로 연결
    private Button alarm;//알람 메인 페이지로 연결
    private Button dday;

    //스케줄정보가 저장된 파일에서 받아온 정보를 저장하기 위한 변수
    private String[] data = new String[32];//일정을 받아오기 위한 string 배열
    private int day;//날짜를 받기 위한 int변수
    private String temp;//스케줄파일에서 한 라인을 가져올 때 사용되는 string 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//월간달력 페이지와 연결된 class

        //xml에서 사용되는 textview와 연결, 일정을 보여줌
        show3 = (TextView) findViewById(R.id.show3);//각 숫자는 해당하는 날짜와 연결되어 있음
        show6 = (TextView) findViewById(R.id.show6);//예) 6일에 해당하는 일정 보여주는 textview
        show15 = (TextView) findViewById(R.id.show15);
        show21 = (TextView) findViewById(R.id.show21);
        show30 = (TextView) findViewById(R.id.show30);

        //xml에서 사용되는 button과 연결, 일정에 대한 세부사항 보여줌
        day3 = (Button) findViewById(R.id.day07);
        day6 = (Button) findViewById(R.id.day13);
        day15 = (Button) findViewById(R.id.day25);
        day21 = (Button) findViewById(R.id.day34);
        day30 = (Button) findViewById(R.id.day46);

        //xml에서 사용되는 button과 연결, 다른 페이지로 전환시킴
        monthly = (Button) findViewById(R.id.main);
        alarm = (Button) findViewById(R.id.alarm);//click시 알람 main페이지를 띄워줌
        dday = (Button)findViewById(R.id.dday);

        //파일로 부터 받은 정보에 관련된 함수
        getdata();//일정을 파일로부터 받는 함수
        show();//해당하는 날짜에 대한 일정을 띄워주는 함수

        //툴바 추가 코드
        Toolbar toolbar;//툴바 정의
        toolbar = (Toolbar) findViewById(R.id.toolbar);//툴바변수를 xml과 연결
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));//title색깔을 하얀색으로 설정
        toolbar.setNavigationIcon(R.drawable.icon);//(xml상에)왼쪽 위에 있는 icon 설정
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌

        //툴바 옵션 메뉴 아이콘 바꾸기
        Drawable plus = getResources().getDrawable(R.drawable.plus);
        Bitmap bitmap = ((BitmapDrawable) plus).getBitmap();//bitmap으로 변환
        Drawable drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));//사이즈 설정 후 drawable로 parsing
        toolbar.setOverflowIcon(drawable);

        //날짜 button click시 evnet 정의
        //3일button click에 대한 event작성
        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day3.setTextColor(getResources().getColor(R.color.Title));//버튼의 text color 설정
                Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);//현재 activity정보와 ShowScheduleDetail class 를 parameter로 전달
                intent.putExtra("day", 3);//Showscheduledetail클래스로 3전달 ('day' -> key값) -> key값으로 어떤 값을 받을지 결정
                startActivity(intent);//해당하는 class로 이동
            }
        });
        //6일button click에 대한 event작성
        day6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day6.setTextColor(getResources().getColor(R.color.Title));
                Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);
                intent.putExtra("day", 6);//Showscheduledetail클래스로 6전달
                startActivity(intent);
            }
        });
        //15일button click에 대한 event작성
        day15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day15.setTextColor(getResources().getColor(R.color.Title));
                Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);
                intent.putExtra("day", 15);//Showscheduledetail클래스로 15전달
                startActivity(intent);
            }
        });
        //21일button click에 대한 event작성
        day21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day21.setTextColor(getResources().getColor(R.color.Title));
                Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);
                intent.putExtra("day", 21);//Showscheduledetail클래스로 21전달
                startActivity(intent);
            }
        });
        //30일button click에 대한 event작성
        day30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day30.setTextColor(getResources().getColor(R.color.Title));
                Intent intent = new Intent(getApplicationContext(), ShowScheduleDetail.class);
                intent.putExtra("day", 30);//Showscheduledetail클래스로 30전달
                startActivity(intent);
            }
        });
        //monthlybutton click에 대한 event 작성
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                show();
            }
        });
        dday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),D_day_main.class);
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

    //파일로 부터 받아온 일정을 해당하는 날짜 textview에 띄워줌
    public void show() {
        show3.setText(data[3]);
        show6.setText(data[6]);
        show15.setText(data[15]);
        show21.setText(data[21]);
        show30.setText(data[30]);
    }

    //toolbar에 메뉴가 최초로 눌렸을 때 호출되는 함수
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //toolbar의 menu 클릭시 추가 페이지 로 넘어가기
    public boolean onOptionsItemSelected(MenuItem item) {
        //res/menu에 있는 toolbar.xml의 id 로 넘어갈 페이지 설정
        if (item.getItemId() == R.id.pencil) {
            Intent intent = new Intent(getApplicationContext(), AddSchedulepencil.class);//수기로 일정 입력
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), AddSchedulePicture.class);//사진으로 일정입력
            startActivity(intent);
        }
        return true;
    }

    //file에서 data추출, data를 string배열에 저장
    public void getdata() {
        //file 읽어오기
        try {
            FileInputStream fis = openFileInput("data0.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            while (true) {
                temp = br.readLine();//파일에서 한줄 읽어오기
                if (temp != null) {
                    StringTokenizer st = new StringTokenizer(temp);//tokenizer을 하기 위해 정의
                    day = Integer.parseInt(st.nextToken("/"));//'/'를 기준으로 tokenizing
                    String schedule = st.nextToken("/");
                    data[day] = schedule;//추출한 일정을 data배열에 저장
                } else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}