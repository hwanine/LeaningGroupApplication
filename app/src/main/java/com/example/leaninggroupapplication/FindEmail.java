package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FindEmail extends AppCompatActivity {

    Button prePage, nextAlert;
    EditText schoolNumber, realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);

        prePage = findViewById(R.id.prevbutton_findE);
        nextAlert = findViewById(R.id.nextbutton_findE);
        schoolNumber = findViewById(R.id.editStdno_findE);
        realName = findViewById(R.id.editName_findE);


        nextAlert.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                reqFinding();
            }

        });

        prePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindEmail.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    public void reqFinding(){

        final String schoolNum = schoolNumber.getText().toString().trim();
        final String realname = realName.getText().toString().trim();

        if(TextUtils.isEmpty(schoolNum))

        { //입력값 비어있을 때 처리
            schoolNumber.setError("Please enter this component");
            schoolNumber.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(realname))

        {
            realName.setError("Please enter this component");
            realName.requestFocus();
            return;
        }

        FindEmailReq findEmailReq = new FindEmailReq(schoolNum, realname);
        findEmailReq.execute();

    }


    class FindEmailReq extends AsyncTask<Void, Void, String> {

        String schoolnumber, realname;

        FindEmailReq(String schoolnumber, String realname) {

            this.schoolnumber = schoolnumber;
            this.realname = realname;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) { //여기랑 php만 수정

            super.onPostExecute(s);

            try {

                //Log.e("here",s);
                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {

                    //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject userJson = obj.getJSONObject("user");

                    //System.out.println(userJson);

                    User user = new User(
                            userJson.getString("email"),
                            userJson.getString("nickname")
                    );

                    String showEmail = user.getNickname(); //반대로 파싱되는 문제..

                    //다이얼로그로 이메일 띄워주기

                    AlertDialog.Builder alt = new AlertDialog.Builder(FindEmail.this);

                    alt.setMessage("입력하신 학번과 이름에 해당하는 이메일은 \n"+ showEmail + " 입니다.")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(FindEmail.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                            );
                AlertDialog alert = alt.create();
                alert.setTitle("이메일 찾기");

                alert.show();

                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Log.e("here",s);
                    Toast.makeText(getApplicationContext(), "입력하신 학번과 이름에 해당하는 이메일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("school_number", schoolnumber);
            params.put("real_name", realname);

            return requestHandler.sendPostRequest(URLS.URL_FIND_EMAIL, params);
        }
    }
}
