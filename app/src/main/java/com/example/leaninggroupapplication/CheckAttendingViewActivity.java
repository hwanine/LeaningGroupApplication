package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CheckAttendingViewActivity extends AppCompatActivity implements View.OnClickListener{ //모임화면을 생성해 한눈에 볼 수 있도록 하는 뷰로, 시간이 되었을 때 참석하겠다고 하면 넘어가야하는 액티비티이다.
    ArrayList<CheckAttendingActivity> attenders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attenidng_view);

        Intent attendIntent = getIntent();
        String dataGroupNum=attendIntent.getStringExtra("group_num");
        Log.d("왔다",dataGroupNum);

        AttendMembers attendTask = new AttendMembers();
        attendTask.execute("http://rkdlem1613.dothome.co.kr/attend.php",dataGroupNum);

        ListView listView = findViewById(R.id.AttendingListView);

        attenders.add(new CheckAttendingActivity("곽송이","20173040","rkdlem1613@changwon.ac.kr", this));

        CheckAttendingListAdapter adapter = new CheckAttendingListAdapter(attenders);
        listView.setAdapter(adapter);
    }
///oncerate 끝

    @Override
    public void onClick(View v){
        View openParentView = (View)v.getParent();
        String position = (String) openParentView.getTag();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        String message = position + ": 해당 유저를 신고하였습니다.(나중에는 이름과 포지션을 매칭시켜 해당 유저 이름을 보여주며 신고했다고 하기. 그리고 신고하시겠습니까? 하며 신고사유를 적는 팝업이 필요! 다이얼로그 메시지 말고";
        dialog.setMessage(message);
        dialog.setPositiveButton("확인",null);
        dialog.setCancelable(false);
        dialog.show();
    }
//////

    public void comment_extraction(String response)  {

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray =jsonObject.getJSONArray("response");
            int count = 0;
            String real_name;
            String school_number;
            String email;
            String warning_count;

            System.out.println(jsonArray.length());
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                real_name = object.getString("real_name");
                school_number = object.getString("school_number");
                email = object.getString("email");
                warning_count = object.getString("warning_count");

                CheckAttendingActivity AttendingData = new CheckAttendingActivity(real_name,school_number,email,this);
                attenders.add(AttendingData);
                count++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /////
    private class AttendMembers extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String group_roomnumber = params[1];

            String output = "";

            String postParameters = "group_roomnumber=" + group_roomnumber;

            try {
                //연결 url 설정
                URL url = new URL(params[0]);

                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();

                conn.setConnectTimeout(10000);

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){


                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    int i = 0 ;
                    for(;;){
                        //웹상에 보이는 텍스트를 라인단위로 읽어 저장
                        String line = br.readLine();
                        if(line == null) {
                            break;
                        }
                        i++;
                        output += line;
                    }
                    Log.d("됬나check",output);

                    br.close();
                }
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }

            return output;
        }


        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {
                //comment_extraction(s);

                //adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
