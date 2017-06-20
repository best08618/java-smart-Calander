package com.example.wyshin.calander;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MonthlySchduleValueDefine extends AppCompatActivity{
    //스케줄을 보여주기 위하여 textview 정의
    private TextView show1;
    private TextView show2;
    private TextView show3;
    private TextView show4;
    private TextView show5;
    private TextView show6;
    private TextView show7;
    private TextView show8;
    private TextView show9;
    private TextView show10;
    private TextView show11;
    private TextView show12;
    private TextView show13;
    private TextView show14;
    private TextView show15;
    private TextView show16;
    private TextView show17;
    private TextView show18;
    private TextView show19;
    private TextView show20;
    private TextView show21;
    private TextView show22;
    private TextView show23;
    private TextView show24;
    private TextView show25;
    private TextView show26;
    private TextView show27;
    private TextView show28;
    private TextView show29;
    private TextView show30;
    //세부 스케줄을 보여주기 위해 button 정의
    private Button day1;
    private Button day2;
    private Button day3;
    private Button day4;
    private Button day5;
    private Button day6;
    private Button day7;
    private Button day8;
    private Button day9;
    private Button day10;
    private Button day11;
    private Button day12;
    private Button day13;
    private Button day14;
    private Button day15;
    private Button day16;
    private Button day17;
    private Button day18;
    private Button day19;
    private Button day20;
    private Button day21;
    private Button day22;
    private Button day23;
    private Button day24;
    private Button day25;
    private Button day26;
    private Button day27;
    private Button day28;
    private Button day29;
    private Button day30;

    private String[] data = new String[32];
    private String temp;
    private int day;

    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main, parent, false);
        }
        show1 = (TextView) convertView.findViewById(R.id.show1);
        show2 = (TextView) convertView.findViewById(R.id.show2);
        show3 = (TextView) convertView.findViewById(R.id.show3);
        show4 = (TextView) convertView.findViewById(R.id.show4);
        show5 = (TextView) convertView.findViewById(R.id.show5);
        show6 = (TextView) convertView.findViewById(R.id.show6);
        show7 = (TextView) convertView.findViewById(R.id.show7);
        show8 = (TextView) convertView.findViewById(R.id.show8);
        show9 = (TextView) convertView.findViewById(R.id.show9);
        show10 = (TextView) convertView.findViewById(R.id.show10);
        show11 = (TextView) convertView.findViewById(R.id.day11);
        show12 = (TextView) convertView.findViewById(R.id.show12);
        show13 = (TextView) convertView.findViewById(R.id.show13);
        show14 = (TextView) convertView.findViewById(R.id.show14);
        show15 = (TextView) convertView.findViewById(R.id.show15);
        show16 = (TextView) convertView.findViewById(R.id.show16);
        show17 = (TextView) convertView.findViewById(R.id.show17);
        show18 = (TextView) convertView.findViewById(R.id.show18);
        show19 = (TextView) convertView.findViewById(R.id.show19);
        show20 = (TextView) convertView.findViewById(R.id.show20);
        show21 = (TextView) convertView.findViewById(R.id.show21);
        show22 = (TextView) convertView.findViewById(R.id.show22);
        show23 = (TextView) convertView.findViewById(R.id.show23);
        show24 = (TextView) convertView.findViewById(R.id.show24);
        show25 = (TextView) convertView.findViewById(R.id.show25);
        show26 = (TextView) convertView.findViewById(R.id.show26);
        show27 = (TextView) convertView.findViewById(R.id.show27);
        show28 = (TextView) convertView.findViewById(R.id.show28);
        show29 = (TextView) convertView.findViewById(R.id.show29);
        show30 = (TextView) convertView.findViewById(R.id.show30);

        day1 = (Button) convertView.findViewById(R.id.day05);
        day2 = (Button) convertView.findViewById(R.id.day06);
        day3 = (Button) convertView.findViewById(R.id.day07);

        day4 = (Button) convertView.findViewById(R.id.day11);
        day5 = (Button) convertView.findViewById(R.id.day12);
        day6 = (Button) convertView.findViewById(R.id.day13);
        day7 = (Button) convertView.findViewById(R.id.day14);
        day8 = (Button) convertView.findViewById(R.id.day15);
        day9 = (Button) convertView.findViewById(R.id.day16);
        day10 = (Button) convertView.findViewById(R.id.day17);

        day11 = (Button) convertView.findViewById(R.id.day21);
        day12 = (Button) convertView.findViewById(R.id.day22);
        day13 = (Button) convertView.findViewById(R.id.day23);
        day14 = (Button) convertView.findViewById(R.id.day24);
        day15 = (Button) convertView.findViewById(R.id.day25);
        day16 = (Button) convertView.findViewById(R.id.day26);
        day17 = (Button) convertView.findViewById(R.id.day27);

        day18 = (Button) convertView.findViewById(R.id.day31);
        day19 = (Button) convertView.findViewById(R.id.day32);
        day20 = (Button) convertView.findViewById(R.id.day33);
        day21 = (Button) convertView.findViewById(R.id.day34);
        day22 = (Button) convertView.findViewById(R.id.day35);
        day23 = (Button) convertView.findViewById(R.id.day36);
        day24 = (Button) convertView.findViewById(R.id.day37);

        day25 = (Button) convertView.findViewById(R.id.day41);
        day26 = (Button) convertView.findViewById(R.id.day42);
        day27 = (Button) convertView.findViewById(R.id.day43);
        day28 = (Button) convertView.findViewById(R.id.day44);
        day29 = (Button) convertView.findViewById(R.id.day45);
        day30 = (Button) convertView.findViewById(R.id.day46);
        //final String strColor = "#fe4365";

        return convertView;
    }

    public void show(){
        show1.setText(data[1]);
        show2.setText(data[2]);
        show3.setText(data[3]);
        show6.setText(data[6]);
        show15.setText(data[15]);
        show21.setText(data[21]);
        show30.setText(data[30]);
    }
    public void getdata(){
        //file 읽어오기
        MainActivity mainActivity = new MainActivity();
        try {
            FileInputStream fis = mainActivity.openFileInput("data9.txt");
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

    public TextView getShow1() {
        return show1;
    }

    public TextView getShow2() {
        return show2;
    }

    public TextView getShow3() {
        return show3;
    }

    public TextView getShow4() {
        return show4;
    }

    public TextView getShow5() {
        return show5;
    }

    public TextView getShow6() {
        return show6;
    }

    public TextView getShow7() {
        return show7;
    }

    public TextView getShow8() {
        return show8;
    }

    public TextView getShow9() {
        return show9;
    }

    public TextView getShow10() {
        return show10;
    }

    public TextView getShow11() {
        return show11;
    }

    public TextView getShow12() {
        return show12;
    }

    public TextView getShow13() {
        return show13;
    }

    public TextView getShow14() {
        return show14;
    }

}
