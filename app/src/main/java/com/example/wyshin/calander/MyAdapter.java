package com.example.wyshin.calander;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{

    //보여줄 정보를 Arraylist를 사용하여 저장
    private ArrayList<MyItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    //Adapter가 가지고 있는 data 보여주기 위한 정의 함수
    public View getView(int position, View convertView, ViewGroup parent) {

        //어플리케이션 환경에 관한 글로벌 정보를 접근하기 위한 인터페이스
        //listview에있는 위젯들을 listview_custom.xml과 연결하기 위한 변수
        Context context = parent.getContext();

        // 'listview_custom' Layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);//listview_custom을 inflate
        }

        //'listview_custom'에 정의된 위젯에 대한 참조 획득
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        final TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;
        TextView tv_contents = (TextView) convertView.findViewById(R.id.tv_contents) ;
        TextView tv_time = (TextView)convertView.findViewById(R.id.tv_time);

        //listview에 띄워줄 값들을 받아오기 위한 클래스 변수
        MyItem myItem = getItem(position);

        //각 위젯에 세팅된 아이템을 보여줌
        iv_img.setImageDrawable(myItem.getIcon());
        tv_name.setText(myItem.getName());
        tv_contents.setText(myItem.getContents());
        tv_time.setText(myItem.getTime());

        return convertView;
    }

    //아이템 data 추가를 위한 함수
    public void addItem(Drawable img, int name, String contents, String hour, String min) {

        //MyItem클래스 인스턴스화
        MyItem mItem = new MyItem();

        //textview로 띄워줄 data 생성
        String name1 = Integer.toString(name);
        String time = hour + "시" + min + "분";

        //MyItem에 값들을 setting해줌
        mItem.setIcon(img);
        mItem.setName(name1);
        mItem.setContents(contents);
        mItem.setTime(time);

        //setting한 item들을 Arraylist에 추가해준다
        mItems.add(mItem);
    }
}