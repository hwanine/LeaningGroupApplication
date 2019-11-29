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

public class ChangePasswd extends AppCompatActivity {

    Button prePage, nextAlert;
    EditText email, schoolNumber, realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);

        prePage = findViewById(R.id.prevbutton_findPass);
        nextAlert = findViewById(R.id.nextbutton_findPass);
        email = findViewById(R.id.editEmail_findPass);
        schoolNumber = findViewById(R.id.editStdno_findPass);
        realName = findViewById(R.id.editName_findPass);

        nextAlert.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                reqChange();

            }
        });

        prePage.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChangePasswd.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void reqChange(){

        final String emailP = email.getText().toString().trim();
        final String schoolNum = schoolNumber.getText().toString().trim();
        final String realN = realName.getText().toString().trim();

        if(TextUtils.isEmpty(emailP)){

            email.setError("Please enter this component");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(schoolNum)){

            schoolNumber.setError("Please enter this component");
            schoolNumber.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(realN)){

            realName.setError("Please enter this component");
            realName.requestFocus();
            return;

        }

        ChangePasswdReq changePasswdReq = new ChangePasswdReq(emailP, schoolNum, realN);
        changePasswdReq.execute();
    }

    class ChangePasswdReq extends AsyncTask<Void, Void, String> {

        String email,schoolnumber, realname;

        ChangePasswdReq(String email, String schoolnumber, String realname) {

            this.email = email;
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

                    final String showEmail = user.getNickname(); //반대로 파싱되는 문제..
                    final String showNickname = user.getEmail();

                    //다이얼로그로 이메일 띄워주기

                    AlertDialog.Builder alt = new AlertDialog.Builder(ChangePasswd.this);

                    alt.setMessage("이메일: "+ showEmail + "\n 닉네임: " + showNickname + " \n 해당 유저의 비밀번호를 변경하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(ChangePasswd.this, NewPasswd.class); //new passwd로 가고 이때 정보 넘겨주기

                                            intent.putExtra("inputEmail", showEmail);
                                            intent.putExtra("inputNickname", showNickname);
                                            startActivity(intent);
                                        }
                                    }
                            )

                            .setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alert = alt.create();
                    alert.setTitle("비밀번호 변경");

                    alert.show();

                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Log.e("here",s);
                    Toast.makeText(getApplicationContext(), "입력하신 정보에 해당하는 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email",email);
            params.put("school_number", schoolnumber);
            params.put("real_name", realname);

            return requestHandler.sendPostRequest(URLS.URL_CHANGE_PASSWD, params);
        }
    }
}
