package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    //이메일 인증을 누르면 자체적으로 중복 검사 후 중복이면 중복 메시지를 반환, 아니면 인증 진행
    //랜덤으로 6자리 난수를 생성해 보내주기, 인증
    //이 인증을 새 activity를 만들건지, 아니면 팝업창으로 진행할건지 상의 필요
    /*public void aithenticationEmail(){
    }*/

    EditText userEmail, userNickname, userPasswd, userSchoolnumber, userRealname;
    Button emailAuthenticationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userEmail = findViewById(R.id.input_email_authentication);
        userNickname = findViewById(R.id.input_nick_name);
        userPasswd = findViewById(R.id.input_password);
        userSchoolnumber = findViewById(R.id.input_school_number);
        userRealname = findViewById(R.id.input_real_name);

        final Intent back = getIntent();
        final String afterEmail;
        final boolean afterAuth;


        if(back.getStringExtra("email") != null){//인텐트가 존재하면

            afterEmail = back.getExtras().getString("email");
            afterAuth = back.getExtras().getBoolean("personAllow");

        }
        else {
            afterEmail = null;
            afterAuth = false;
        }

        emailAuthenticationButton = findViewById(R.id.input_email_authentication_button);
        emailAuthenticationButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) { //이메일 인증 버튼

                final String email = userEmail.getText().toString().trim();
                Auth auth = new Auth(email);
                auth.execute();

            }
        });

        findViewById(R.id.join).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(afterAuth){

                    userEmail.setText(String.valueOf(afterEmail));
                    registerUser();

                }else{

                    Toast.makeText(getApplicationContext(),"이메일 인증을 먼저 진행 해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(){

        final String email = userEmail.getText().toString().trim();
        final String nickname = userNickname.getText().toString().trim();
        final String passwd = userPasswd.getText().toString().trim();
        final String school_number = userSchoolnumber.getText().toString().trim();
        final String real_name = userRealname.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            userEmail.setError("Please enter this component");
            userEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(nickname)){
            userNickname.setError("Please enter this component");
            userNickname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passwd)){
            userPasswd.setError("Please enter this component");
            userPasswd.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(school_number)){
            userSchoolnumber.setError("Please enter this component");
            userSchoolnumber.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(real_name)){
            userRealname.setError("Please enter this component");
            userRealname.requestFocus();
            return;
        }

        RegisterUser ru = new RegisterUser(email, nickname, passwd, school_number, real_name);
        ru.execute();
    }

    private class Auth extends AsyncTask<Void, Void, String>{

        private String email;

        Auth(String email){
            this.email = email;
        }

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email",email);

            return requestHandler.sendPostRequest(URLS.URL_AVAIL_AUTH, params);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{

                JSONObject obj = new JSONObject(s);

                if(!obj.getBoolean("error")){ //에러 미발생

                    AlertDialog.Builder alt = new AlertDialog.Builder(SignInActivity.this);

                    alt.setMessage("사용 가능한 이메일 입니다. \n "+  email +"\n 해당 이메일로 인증번호를 보내시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("네",
                                    new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int id){
                                            //네 클릭
                                            //Intent intent = new Intent(SignInActivity.this, Authentication.class);
                                            //startActivity(intent);


                                            Intent intent = new Intent(SignInActivity.this, Authentication.class);
                                            intent.putExtra("inputEmail",email);

                                            startActivity(intent);
                                        }

                                    })
                            .setNegativeButton("아니오",
                                    new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int id){
                                            //아니오 클릭
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alert = alt.create();
                    alert.setTitle("이메일 인증 요청");

                    alert.show();

                }else{
                    Toast.makeText(getApplicationContext(), "이미 존재하는 사용자입니다.", Toast.LENGTH_SHORT).show();
                    //에러 발생
                }

            }catch (JSONException e ){
                e.printStackTrace();
            }

        }
    }
    private class RegisterUser extends AsyncTask<Void, Void, String>{

        private String email, nickname, passwd, school_number, real_name;
        RegisterUser(String email, String nickname, String passwd, String school_number, String real_name){

            this.email = email;
            this.nickname = nickname;
            this.passwd = passwd;
            this.school_number = school_number;
            this.real_name = real_name;

        }

        @Override
        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email",email);
            params.put("nickname", nickname);
            params.put("passwd",passwd);
            params.put("school_number",school_number);
            params.put("real_name", real_name);

            return requestHandler.sendPostRequest(URLS.URL_REGISTER, params);
        }

        @Override
        protected void onPostExecute(String s){

            super.onPostExecute(s);
            Log.i("SignUp","Info" + s );

            try{

                JSONObject obj = new JSONObject(s);

                if(!obj.getBoolean("error")){

                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject userJson = obj.getJSONObject("user");

                    User user = new User(

                            userJson.getString("email"),
                            userJson.getString("nickname")
                    );

                    PrefManager.getInstance(getApplicationContext()).setUserLogin(user);
                    finish();

                    String inputEmail = user.getEmail();
                    String inputNickname = user.getNickname();

                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("inputEmail",inputEmail);
                    intent.putExtra("inputNickname", inputNickname);

                    startActivity(intent);

                }else{
                  Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();
                }

            }catch (JSONException e ){
                e.printStackTrace();
            }

        }
    }
}
