package com.example.wyshin.calander;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class D_day extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    /* user가 설정한 d_day 날짜와 결정된 d_day를 출력하기 위한 변수이다*/
    private TextView ddayText;
    private TextView resultText;
    /*오늘 날짜를 받아와서 저장할 변수들이다. */
    private int todayYear;
    private int todayMonth;
    private int todayDay;
    private String dataResult;
    /* user가 선택한 date 값을 저장할 변수들이다*/
    private int dYear = 1;
    private int dMonth = 1;
    private int dDay = 1;

    private Button enterDateButton; //DATE를 선택할 버튼을 위한 변수이다.
    private ImageButton iconButton; // USER가 선택한 icon 으로 연결해주기 위한 변수이다.
    private String icon;  //user가 무엇을 선택하느냐에 따라 icon에 string으로 값을 주어 이후 string값으로 사진을 불러오기 위해 만든 변수이다.
    private Bitmap bp;    // 사진을 bitmap으로 바꿔 저장하기 위한 변수이다.
    private double ddayMill;       //d_day날짜를 밀리타임으로 바꿔 저장할 변수이다.
    private double todayMill;        //오늘날짜를 밀리타임으로 바꿔 저장할 변수이다.
    private double resultMill;       //밀리타임끼리의 차이를 계산할 변수이다.
    private double resultNumber = 0;   //밀리타임을 day로 바꿔 저장할 변수이다.
    private Dialog dialog;           // more의 경우 dialog를 띄우기 위한 변수이다.
    private String resultString; //  계산된 d_day, user 가 입력한 내용 ,  icon등을 저장할 변수로 이후 file에 저장할 string이다.
    static final int DATE_DIALOG_ID = 0;    //DATE PICKER 에서 사용할 상수이다.

    /* 사진을 연결하기 위한 변수들이다(camera , 앨범으로 연결)*/
    final int REQUEST_PICTURE = 1;  //카메라로 연결하여 사진을 전송시 되돌려 받는 번호이다.
    final int REQUEST_PHOTO_ALBUM = 2;//앨범으로 연결하여 사진을 전송시 되돌려 받는 번호이다.
    private String SAMPLING = ".png";  //첫번째 이미지 샘플변수이다.sample로 미리 값을 넣어줌.

    @Override
    public void onCreate(Bundle savedInstanceState) {   //oncreate는 이 class에 들어올때 항상 실행되는 함수이다.
        icon = "/";  //먼저 icon을 / 로 초기화 해주었다 . 왜냐하면 icon을 user가 선택하지 않고 more을 통해 이미지로 선택하는 경우에
        // default값이 들어감을 방지하기 위함이다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_day);     //이 class 는 activity_d_day와 연결되어 있다.

        try { // 파일을 열때는 제대로 열리지 않은 경우를 위해 try_catch 구문을 사용하여 예외처리를 해준다
            FileOutputStream fos = openFileOutput("data_input2.txt", Context.MODE_APPEND);   //먼저 data를 저장할 파일을 열어준다.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        findViewById(R.id.getCustom).setOnClickListener(this); // getCustom button을 listener와 연결해주어 이후 버튼이 눌렸을때, listenr에서 다른  instruction을 실행 할 수 있도록 하였다.
        bp = BitmapFactory.decodeResource(getResources(), R.drawable.birthday); // 이미지를 선택하지 않은 경우에도 image 파일에 기본 값을 저장해주기 위해
        // birthday이미지를 bitmap으로 바꿔  기본 이미지로 설정해주었다.

         /* xml과 변수들을 id값을 통해 연결해준 것이다*/
        ddayText = (TextView) findViewById(R.id.d_day);
        resultText = (TextView) findViewById(R.id.result);
        enterDateButton = (Button) findViewById(R.id.dateButton);

        enterDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { //dateButton이 눌릴경우 listner와 연결하여 밑의 함수를 실행
                // TODO Auto-generated method stub
                showDialog(0);//showDialog는 dialog 형태로 form 을 보여주는 것으로 date를 선택하는 버튼을 누르면 dialog가 뜨게 된다.
            }
        });

 /*---------------오늘날짜의 연도, 달, 날짜를 각각 변수에 저장해준다-------------*/
        Calendar calendar = Calendar.getInstance();              //날짜를 불러오고 위해 calendar라는 class를 이용하였고 오늘날짜는 calendar변수에 저장.
        todayYear = calendar.get(Calendar.YEAR);
        todayMonth = calendar.get(Calendar.MONTH);
        todayDay = calendar.get(Calendar.DAY_OF_MONTH);

         /*---------------오늘날짜의 연도, 달, 날짜를 각각 변수에 저장해준다-------------*/
        Calendar dCalendar = Calendar.getInstance();            //날짜를 불러오기 위해 calendar라는 class를 이용하였고 d_day날짜는 dCalendar변수에 저장.
        dCalendar.set(dYear, dMonth, dDay);                  //dC

        todayMill = calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        ddayMill = dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        resultMill = (ddayMill - todayMill) / (24 * 60 * 60 * 1000);          //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

        resultNumber =  resultMill + 1;                            // 이 변수 설정들은 datepicker가 선택되기 전에 기본 setting을 하기 위함이다.

    }//OnCreate end

    public void onClick(View v) { //listner에 연결된 button이 클릭된 경우 실행되는 함수이다.

        //--------------------user가 more button을  클릭한 경우------------------------//
        if (v.getId() == R.id.getCustom) {
            EditText content = (EditText) findViewById(R.id.content);
            String str_receiver = content.getText().toString();
            dataResult = str_receiver + " " + resultString + " " + icon + " " + "\n"; // 다른 page 로 넘어가면서 정보가 날라가게 되므로 미리 값들을 받아 저장하기 위한 변수이다.

            try {
                FileOutputStream fos = openFileOutput("data_input2.txt", Context.MODE_APPEND);    // 위에서 받은 임시 결과값을 file에다 넣어준다.
                PrintWriter pw = new PrintWriter(fos);
                pw.print(dataResult);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(D_day.this, "catch_store", Toast.LENGTH_LONG).show();
            }

/*--------------------dialog를 띄우고 이후 실행할 것을 위한 함수들이다-------------*/
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //먼저 다이어로그를 build를 통해 만들어낸다.
            View chooseLayout = View.inflate(this, R.layout.dialog, null);//어떤 layout을 다이어로그에 띄울것인지 정해준다.
            builder.setView(chooseLayout);//현재 빌더에 우리가만든 다이어로그 레이아웃뷰를 추가해준다.
            chooseLayout.findViewById(R.id.camera).setOnClickListener(this);//다이어로그의 버튼에  카메라와 사진앨범의 클릭 버튼을 listenr로 연결해준다.
            chooseLayout.findViewById(R.id.photoAlbum).setOnClickListener(this);
            dialog = builder.create(); //지금까지 만든 builder를 생성하고 띄어준다.
            dialog.show();

        } else if (v.getId() == R.id.camera) { //카메라버튼을 누른 경우 다이어로그를 끄고 사진을 찍는 함수를 불러오게 된다.

            dialog.dismiss();
            loadPicture();
        } else if (v.getId() == R.id.photoAlbum) {//앨범을 누른 경우 다이어로그를 끄고 앨범을 불러오는 함수를 불러온다.
            dialog.dismiss();
            photoAlbum();
        }

    }

    private void updateDisplay() {   //계산한 정보를 띄어주기 위한 함수이다.
        ddayText.setText(String.format("%d년 %d월 %d일", dYear, dMonth + 1, dDay));

        if (resultNumber >=0) {     // 디데이 날짜가 오늘날짜 보다 뒤일경우  즉 계산한 값이 - 이거나 0일 경우는 -로 print해준다.
            resultString = "D-" + (int)resultNumber;
            resultText.setText(resultString);
        } else { // 반대일경우  +로 print해준다.
            int absR = (int)Math.abs(resultNumber);
            resultString = "D+" + (absR);
            resultText.setText(resultString);
        }
    }

    public void submit(View v) { //user가 submit을 누른경우
        /*user가 입력한 정보를 한번에 string으로 저장해준다*/
        EditText content = (EditText) findViewById(R.id.content);
        String str_receiver = content.getText().toString();
        dataResult = str_receiver + " " + resultString + " " + icon + " " + "\n";

        try { // 결과 string을 file에 넣어준다.
            FileOutputStream fos = openFileOutput("data_input2.txt", Context.MODE_APPEND);
            PrintWriter pw = new PrintWriter(fos);
            pw.print(dataResult);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(D_day.this, "catch_store", Toast.LENGTH_LONG).show();
        }

        try { //submmit을 누른경우는 icon을 선택한 경우이므로 image 가 deafult이므로 기본 image값을 넣어준다.
            FileOutputStream fos = openFileOutput("image_input2.txt", Context.MODE_APPEND);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(bitmapToString(bp));
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(D_day.this, "catch_store", Toast.LENGTH_LONG).show();
        }
        Intent sbm = new Intent(D_day.this, D_day_main.class); //입력한 값을 가지고 main으로 넘어가 띄어주게 된다.
        startActivity(sbm);
    }

    /*onclick 함수들로 xml에서 미리 onclick을 정의했기 때문에 이름으로 함수를 정의할 수 있고 각각의 버튼이 누르면 icon 이
    각 image에 맞는 이름으로 저장되게 되며, 선택된 view의 background의 색도 바꿔준다.
     */
    public void love(View v) {
        iconButton = (ImageButton) findViewById(R.id.loveButton);
        iconButton.setBackgroundColor(Color.argb(100, 247, 170, 151));
        icon = "love";
    }

    public void ring(View v) {
        iconButton = (ImageButton) findViewById(R.id.ring);
        iconButton.setBackgroundColor(Color.argb(100, 247, 170, 151));
        icon = "ring";
    }

    public void airplane(View v) {
        iconButton = (ImageButton) findViewById(R.id.airplane);
        iconButton.setBackgroundColor(Color.argb(100, 247, 170, 151));
        icon = "airplane";
    }

    public void exam(View v) {
        iconButton = (ImageButton) findViewById(R.id.exam);
        iconButton.setBackgroundColor(Color.argb(100, 247, 170, 151));
        icon = "exam";
    }

    public void birthday(View v) {
        iconButton = (ImageButton) findViewById(R.id.birthday);
        iconButton.setBackgroundColor(Color.argb(100, 247, 170, 151));
        icon = "birthday";
    }

    public void star(View v) {
        iconButton = (ImageButton) findViewById(R.id.star);
        iconButton.setBackgroundColor(Color.argb(100, 247, 170, 151));
        icon = "star";
    }
 /* ---------------------Date Picker를 위한 함수들이다---------------------------*/

    /*사용자가 누를 날짜를 저장하는 함수이다*/
    private DatePickerDialog.OnDateSetListener dDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { //dataePicker에서 사용자가 선택한 날짜를 받아오게된다.
            // TODO Auto-generated method stub
            dYear = year;        //Listener로 부터 받아온 정보들을 d_day 변수들에 각각 저장해준다.
            dMonth = monthOfYear;
            dDay = dayOfMonth;
            final Calendar dCalendar = Calendar.getInstance();
            dCalendar.set(dYear, dMonth, dDay);

            ddayMill = dCalendar.getTimeInMillis();     //d_day값을 밀리시간으로 바꿔준다.
            resultMill = (ddayMill - todayMill) / (24 * 60 * 60 * 1000); //오늘날짜의 밀리시간과 디데이 밀리시간을 뺴주면서 결과를 저장한다.

            resultNumber =  resultMill ;
            if (resultNumber < 0 ) {      //만약 0보다 작은경우는 뺄때 d_day에서 보여줄때는 +1 이 필요하므로 더 -1을 해줘서 이후 abs로 표현될수 있게 해준다.
                resultNumber= resultNumber - 1 ;
            }
            updateDisplay();
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {  /* */
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this, dDateSetListener, todayYear, todayMonth, todayDay);

        }
        return null;
    }

    void photoAlbum() { //앨범에 저장된 사진을 불러오는 함수이다. 앨범의 인텐트는 ACTION_PICK 이므로 연결해준다.

        Intent intent = new Intent(Intent.ACTION_PICK);//앨범의 인텐트는 ACTION_PICK 이므로 연결해준다.

        intent.setType(MediaStore.Images.Media.CONTENT_TYPE); //갤러리리의 기본설정 해주는 것인데, 이미지와그경로를 표준타입으로 설정하는 것이다.

        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //사진이 저장된위치(sdcard)에 데이터가 잇다고 지정해준다.

        startActivityForResult(intent, REQUEST_PHOTO_ALBUM);

    }

    Bitmap loadPicture() {//사진찍은 것을 로드 해오는데 사이즈를 조절하기위한 함수이다.

        File file = new File(Environment.getExternalStorageDirectory(), SAMPLING); //먼저 파일을 가져온다.

        BitmapFactory.Options options = new BitmapFactory.Options(); //현재사진찍은 것을 조절하기위해 조절하는 클래스를 만들어준다

        options.inSampleSize = 4; //사진의 사이즈를 결정해준다.

        return BitmapFactory.decodeFile(file.getAbsolutePath(), options); //조정한 사진을 리턴해준다.

    }

    /*bitmap정보를 string으로 바꿔주는 함수로 이는 사진의 정보를 bitmap으로 바꿔 file에 저장하기 위함이다.*/
    public static String bitmapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (NullPointerException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
    /*사진찍기, 앨범에서 가져오기 함수에서 activity 요청한 후 그 결과를 받아들이는 함수이다*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_PICTURE) {//사진을 찍은경우로, 그사진을 로드해온다.

                Intent intent = new Intent(this, D_day_main.class);
                try {
                    FileOutputStream fos = openFileOutput("image_input2.txt", Context.MODE_APPEND); // 저장할  파일을 열고
                    PrintWriter pw = new PrintWriter(fos);
                    pw.println(bitmapToString(loadPicture()));        //사진을 bitmap형식이아닌 string형식으로 바꿔 저장해준다.
                    pw.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "catch_store", Toast.LENGTH_LONG).show();
                }

                startActivity(intent);
            }

            if (requestCode == REQUEST_PHOTO_ALBUM) {
                Intent intent = new Intent(this, D_day_main.class);
                try {
                    FileOutputStream fos = openFileOutput("image_input2.txt", Context.MODE_APPEND); // 저장할  파일을 열고
                    PrintWriter pw = new PrintWriter(fos);
                    pw.println(bitmapToString(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()))); //사진을 bitmap형식이아닌 string형식으로 바꿔 저장해준다.
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "catch_store", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        }

    }


}//DatecalActivity end
