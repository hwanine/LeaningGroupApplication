package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckAttendingViewActivity extends AppCompatActivity implements View.OnClickListener{ //모임화면을 생성해 한눈에 볼 수 있도록 하는 뷰로, 시간이 되었을 때 참석하겠다고 하면 넘어가야하는 액티비티이다.
    ArrayList<CheckAttendingActivity> attenders = new ArrayList<>();
    CheckAttendingListAdapter adapter;

    ArrayList<String> getPos = new ArrayList<>();

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

        adapter = new CheckAttendingListAdapter(attenders);
        listView.setAdapter(adapter);
    }
///oncerate 끝

    @Override
    public void onClick(View v){

        View openParentView = (View)v.getParent();
        final String position = (String) openParentView.getTag();

        EditText input = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog.setMessage("해당 유저를 신고하는 사유를 적어주십시오. \n (예, 실제 모임에 불참석하고 참석 확인/ \n 실제 모임에서 불쾌한 언행을 행사 등... )");
        dialog.setView(input);
        dialog.setCancelable(false);
        dialog.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int getI = 0;

                        for(int i=0; attenders.size() > i; i++){

                            if(getPos.get(i).equals(attenders.get(i).EMail) == true){

                                getI = i;

                            }else{

                                getI = 0;
                                Toast.makeText(getApplicationContext(),"에러",Toast.LENGTH_SHORT).show();
                            }
                        }

                        //AsyncTask객체 생성 신고하는거!
                        ReportUser reportUser = new ReportUser(attenders.get(getI).EMail,attenders.get(getI).SchoolNumber,attenders.get(getI).Name);
                        reportUser.execute();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        dialog.show();

        /*String message = position + ": 해당 유저를 신고하였습니다.(나중에는 이름과 포지션을 매칭시켜 해당 유저 이름을 보여주며 신고했다고 하기. 그리고 신고하시겠습니까? 하며 신고사유를 적는 팝업이 필요! 다이얼로그 메시지 말고";
        dialog.setMessage(message);
        dialog.setPositiveButton("확인",null);
        dialog.setCancelable(false);
        dialog.show();*/
    }
//////

    class ReportUser extends AsyncTask<Void, Void, String>{

        String uEmail, schoolnumber, realname;


        ReportUser(String email, String schoolnumber, String realname){

            this.uEmail = email;
            this.schoolnumber = schoolnumber;
            this.realname = realname;

            Log.i("이름1:", email);
            Log.i("이름2:", schoolnumber);
            Log.i("이름3:", realname);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();

            params.put("email",uEmail);
            params.put("school_number", schoolnumber);
            params.put("real_name", realname);

            return requestHandler.sendPostRequest(URLS.URL_REPORT_ON, params);
        }
//
        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);

            try{

                JSONObject obj = new JSONObject(s);

                if(obj.getBoolean("error")){

                    Toast.makeText(getApplicationContext(),"신고 실패", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"해당 유저를 신고하였습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

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
                getPos.add(email);

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
                comment_extraction(s);

                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
