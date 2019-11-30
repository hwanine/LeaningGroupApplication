package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    LinearLayout LanguageFrame;
    LinearLayout LicenseFrame;
    LinearLayout ReadingFrame;
    LinearLayout MajoringFrame;
    LinearLayout HobbyFrame;
    ListView LanguageFrameListView;
    ListView LicenseFrameListView;
    ListView ReadingFrameListView;
    ListView MajoringFrameListView;
    ListView HobbyFrameListView;

    TextView Title;
    TextView GroupDate;
    TextView Writer;
    TextView GroupNumOfMem;
    TextView GroupNumber;
    ArrayList<createGroupSummaryObject> summaryObject;
    ListAdapter adapter;

    String category;

    TextView loginUserEmail;
    TextView loginUserNickname;
    Button loginButton;
    User user;
    int getAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Title=findViewById(R.id.Title);
        GroupDate=findViewById(R.id.GroupDate);
        Writer=findViewById(R.id.Writer);
        GroupNumOfMem=findViewById(R.id.GroupNumOfMem);
        GroupNumber=findViewById(R.id.GroupNumber);


        summaryObject = new ArrayList<>();

        adapter = new ListAdapter(summaryObject);

        final PrefManager prefManager = PrefManager.getInstance(MainActivity.this);
        user = prefManager.getUser();

        setContentView(R.layout.activity_main);

        LanguageFrameListView =(ListView)findViewById(R.id.LanguageFrameListView);
        LicenseFrameListView = (ListView)findViewById(R.id.LicenseFrameListView);
        ReadingFrameListView = (ListView)findViewById(R.id.ReadingFrameListView);
        MajoringFrameListView = (ListView)findViewById(R.id.MajoringFrameListView);
        HobbyFrameListView = (ListView)findViewById(R.id.HobbyFrameListView);


//1
        LanguageFrameListView.setAdapter(adapter);
        LanguageFrameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l){
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("모임정보 확인");
                builder.setMessage("모임을 보시겠습니까?.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getItemAtPosition(position);

                        Intent groupScreenIntent = new Intent(getApplicationContext(), GroupScreen.class);
                        groupScreenIntent.putExtra("nickname",String.valueOf(user.getEmail()));
                        groupScreenIntent.putExtra("group_number",summaryObject.get(position).GroupNum);
                        startActivity(groupScreenIntent);
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();


            }
        });
//2
        LicenseFrameListView.setAdapter(adapter);
        LicenseFrameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l){
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("모임정보 확인");
                builder.setMessage("모임을 보시겠습니까?.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getItemAtPosition(position);

                        Intent groupScreenIntent = new Intent(getApplicationContext(), GroupScreen.class);
                        groupScreenIntent.putExtra("nickname",String.valueOf(user.getEmail()));
                        groupScreenIntent.putExtra("group_number",summaryObject.get(position).GroupNum);
                        startActivity(groupScreenIntent);
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();


            }
        });
//3
        MajoringFrameListView.setAdapter(adapter);
        MajoringFrameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l){
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("모임정보 확인");
                builder.setMessage("모임을 보시겠습니까?.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getItemAtPosition(position);

                        Intent groupScreenIntent = new Intent(getApplicationContext(), GroupScreen.class);
                        groupScreenIntent.putExtra("nickname",String.valueOf(user.getEmail()));
                        groupScreenIntent.putExtra("group_number",summaryObject.get(position).GroupNum);
                        startActivity(groupScreenIntent);
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();


            }
        });
//4
        LanguageFrameListView.setAdapter(adapter);
        LanguageFrameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l){
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("모임정보 확인");
                builder.setMessage("모임을 보시겠습니까?.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getItemAtPosition(position);

                        Intent groupScreenIntent = new Intent(getApplicationContext(), GroupScreen.class);
                        groupScreenIntent.putExtra("nickname",String.valueOf(user.getEmail()));
                        groupScreenIntent.putExtra("group_number",summaryObject.get(position).GroupNum);
                        startActivity(groupScreenIntent);
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();


            }
        });
//5
        HobbyFrameListView.setAdapter(adapter);
        HobbyFrameListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l){
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("모임정보 확인");
                builder.setMessage("모임을 보시겠습니까?.");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterView.getItemAtPosition(position);

                        Intent groupScreenIntent = new Intent(getApplicationContext(), GroupScreen.class);
                        groupScreenIntent.putExtra("nickname",String.valueOf(user.getEmail()));
                        groupScreenIntent.putExtra("group_number",summaryObject.get(position).GroupNum);
                        startActivity(groupScreenIntent);
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();


            }
        });


        loginUserEmail = (TextView)findViewById(R.id.getEmail);
        loginUserNickname = (TextView)findViewById(R.id.getNickName);
        loginButton = (Button)findViewById(R.id.loginbutton);



        if(prefManager.isLoggedIn()){

            loginUserEmail.setText(String.valueOf(user.getEmail()));
            loginUserNickname.setText(String.valueOf(user.getNickname()));

            loginButton.setText("로그아웃"); //로그인 되어있을 경우 로그아웃으로
            loginButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    finish();
                    prefManager.logout();
                }
            });

        }else{ //로그인 안 되어있을 경우

            loginButton.setText("로그인");
            loginButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }
        //Intent intent = getIntent();
        //loginUserEmail.setText(intent.getStringExtra("userEmail"));
        //loginUserNickname.setText(intent.getStringExtra("userNickname"));

        //버튼들을 정의, 프레임 레이아웃에서 다른 프레임으로 넘어가기 위함
        Button LanguageButton = (Button) findViewById(R.id.LanguageButton);//언어
        LanguageButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                changeView(0);
                category="언어";
                summaryObject.clear();
                adapter.notifyDataSetChanged();
                LanguageFrameListView.setAdapter(adapter);
                NetworkTask nt = new NetworkTask();
                nt.execute();
            }
        });

        Button LicenseButton = (Button) findViewById(R.id.LicenseButton);//자격증
        LicenseButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                changeView(1);
                category="자격증";
                summaryObject.clear();
                adapter.notifyDataSetChanged();
                LicenseFrameListView.setAdapter(adapter);
                NetworkTask nt= new NetworkTask();
                nt.execute();
            }
        });

        Button ReadingButton = (Button) findViewById(R.id.ReadingButton);//독서
        ReadingButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                changeView(2);
                category="독서";
                summaryObject.clear();
                adapter.notifyDataSetChanged();
                ReadingFrameListView.setAdapter(adapter);
                NetworkTask nt= new NetworkTask();
                nt.execute();
            }
        });

        Button MajoringButton = (Button) findViewById(R.id.MajoringButton);//전공
        MajoringButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                changeView(3);
                category="전공";
                summaryObject.clear();
                adapter.notifyDataSetChanged();
                MajoringFrameListView.setAdapter(adapter);
                NetworkTask nt= new NetworkTask();
                nt.execute();
            }
        });

        Button HobbyButton = (Button) findViewById(R.id.HobbyButton);//취미
        HobbyButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                changeView(4);
                category="취미";
                summaryObject.clear();
                adapter.notifyDataSetChanged();
                HobbyFrameListView.setAdapter(adapter);
                NetworkTask nt= new NetworkTask();
                nt.execute();
            }
        });


        //버튼을 클릭하면 각 버튼에 대한 프레임을 실행하기 위해 findView하고 하나의 프레임만 남아있게 제거하는 과정

        LanguageFrame = (LinearLayout) findViewById(R.id.LanguageFrame);
        LicenseFrame = (LinearLayout) findViewById(R.id.LicenseFrame);
        ReadingFrame = (LinearLayout) findViewById(R.id.ReadingFrame);
        MajoringFrame = (LinearLayout) findViewById(R.id.MajoringFrame);
        HobbyFrame = (LinearLayout) findViewById(R.id.HobbyFrame);

        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);

        frame.removeView(LicenseFrame);
        frame.removeView(ReadingFrame);
        frame.removeView(MajoringFrame);
        frame.removeView(HobbyFrame);
        frame.removeView(LicenseFrameListView);
        frame.removeView(ReadingFrameListView);
        frame.removeView(MajoringFrameListView);
        frame.removeView(HobbyFrameListView);


        /*Button checkUserListButton = (Button) findViewById(R.id.checkUserListButton); //회원정보를 확인하기 위한 버튼
        checkUserListButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) { //유저리스트를 확인하기 위한 버튼을 클릭하면 백그라운드에서 실행
                new BackgroundTask().execute();
            }
        });*/
        //NetworkTask nt= new NetworkTask();
        //nt.execute();

        //모임작성화면으로 넘어가는 버튼
        Button setupButton;
        setupButton = (Button)findViewById(R.id.setupButton);
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefManager.isLoggedIn()) {

                    Intent intent = new Intent(MainActivity.this, CreateGroup.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), "로그인 후에 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
//온크리에이트 끝



    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String target;
        String Title,GroupDate,Writer,GroupNumOfMem,GroupNumber;

        /*public NetworkTask(String Title,String GroupDate,String Writer,String GroupNumOfMem,String GroupNumber) {
            this.Title = Title;
            this.GroupDate = GroupDate;
            this.Writer = Writer;
            this.GroupNumOfMem =  GroupNumOfMem;
            this.GroupNumber = GroupNumber;
        }*/

        protected  void onPreExecute(){
            target = "http://rkdlem1613.dothome.co.kr/gettest.php";
        }

        @Override
        protected String doInBackground(Void... Voids) {
            try{//
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                String postParameters = "category=" + category;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                    System.out.println(temp);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray =jsonObject.getJSONArray("response");
                int count = 0;
                String category;
                String title;
                String group_roomnumber;
                String member_number;
                String date;
                String starttime;
                String endtime;
                String writer;
                System.out.println(jsonArray.length());
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    group_roomnumber = object.getString("group_roomnumber");
                    //category = object.getString("category");
                    member_number =object.getString("member_number");
                    title = object.getString("group_room_name");
                    date = object.getString("meeting_date");
                    // starttime = object.getString("meeting_start_time");
                    // endtime = object.getString("meeting_end_time");
                    writer = object.getString("writer");
                    createGroupSummaryObject infrom = new createGroupSummaryObject(title, date, writer,member_number,group_roomnumber );
                    summaryObject.add(infrom);
                    adapter.notifyDataSetChanged();
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

////


    public void onClick_log(View view) {
        String nic = (String) loginUserEmail.getText();
        Intent intent = new Intent(this, LogActivity.class);
        intent.putExtra("nic", nic);
        startActivity(intent);
    }


   /* class BackgroundTask extends AsyncTask<Void, Void, String> { //모든 회원에 대한 정보를 가져오기 위한 쓰레드 ,db연결 테스트 용 후에 버튼을 관리자만 볼 수 있게 바꾸기

        String target;

        @Override
        protected void onPreExecute() {
            //post.php는 파싱으로 가져올 웹페이지, 서버 페이지
            target = "http://rkdlem1613.dothome.co.kr/connectTest.html";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성성

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //url을 이용해 웹 페이지에 연결
                InputStream inputStream = httpURLConnection.getInputStream();// 바이트 단위 입력 스트림 생성 소스
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //웹페이지 출력물을 버퍼로 받음(속도 문제)

                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim(); //앞뒤 공백 제거하고 스트링리턴

            } catch (Exception e) {
                e.printStackTrace();
            }

            String string = new String("여기서 널 리턴"); //파싱 테스트 구문
            return string;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, testDBConnect.class);
            intent.putExtra("userList", result); //파싱한 값을 넘겨줌
            MainActivity.this.startActivity(intent); //testDBconnect 액티비티로 넘어감
        }
    }*/

    private void changeView(int index) { //버튼을 클릭함에 따라 프레임 뷰를 바꾸기 위함

        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        frame.removeViewAt(0);

        switch (index) {
            case 0:
                frame.addView(LanguageFrame);
                break;
            case 1:
                frame.addView(LicenseFrame);
                break;
            case 2:
                frame.addView(ReadingFrame);
                break;
            case 3:
                frame.addView(MajoringFrame);
                break;
            case 4:
                frame.addView(HobbyFrame);
                break;
        }
    }

}