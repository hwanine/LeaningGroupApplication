package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPasswd extends AppCompatActivity {

    EditText newPasswd, newPasswdCheck;
    Button pre, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_passwd);

        newPasswd = findViewById(R.id.edit_change_password_findPass);
        newPasswdCheck = findViewById(R.id.edit_confirm_password_findPass);
        pre = findViewById(R.id.prebutton_findPass);
        next = findViewById(R.id.nextbutton_findPass);

        final Intent userInfo = getIntent();
        final String getEmail;
        final String getNickname;

        if (userInfo.getStringExtra("inputEmail") != null) {

            getEmail = userInfo.getExtras().getString("inputEmail");
            getNickname = userInfo.getExtras().getString("inputNickname");

        } else {

            getEmail = null;
            getNickname = null;
        }

        pre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewPasswd.this, ChangePasswd.class);
                startActivity(intent);

            }
        });

        next.setOnClickListener(new View.OnClickListener() { //입력다하고 다음 누른 경우

            @Override
            public void onClick(View v) {

                registerNewPasswd(getEmail, getNickname);
            }
        });
    }

    public boolean checkPasswdSecurity(String passwd) {

        String pPattern = "^(?=.*\\d)(?=.*[~!@#$%^&*()-])(?=.*[a-z]).{8,14}$";
        Matcher matcher = Pattern.compile(pPattern).matcher(passwd);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    private void registerNewPasswd(String email, String nickname){

        final String passwd = newPasswd.getText().toString().trim();
        final String checkPasswd = newPasswdCheck.getText().toString().trim();

        if(TextUtils.isEmpty(passwd)){

            newPasswd.setError("Please enter this component");
            newPasswd.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(checkPasswd)){

            newPasswdCheck.setError("Please enter this component");
            newPasswdCheck.requestFocus();
            return;
        }

        if(checkPasswdSecurity(passwd)){ // 비밀번호 안전성 체크 통과

            if(!checkPasswd.equals(passwd)){ //두개를 똑같이 입력하지 못한 경우

                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 재입력 바랍니다", Toast.LENGTH_SHORT).show();
            }else{ //비밀번호와 비밀버호 체크가 같게 입력된 경우

                RegisterPasswd rp = new RegisterPasswd(email, nickname, passwd);
                rp.execute();
            }

        }else{

            Toast.makeText(this, "비밀번호가 안전성 정책에 어긋납니다. 규칙대로 비밀번호를 생성해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private class RegisterPasswd extends AsyncTask<Void, Void, String> {

        private String  email, nickname, passwd;

        RegisterPasswd( String passwd, String email, String nickname) {

            this.passwd = passwd;
            this.email = email;
            this.nickname = nickname;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("passwd", passwd);
            params.put("email", email);
            params.put("nickname", nickname);

            return requestHandler.sendPostRequest(URLS.URL_NEW_PASSWD, params);
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {


                    //Toast.makeText(getApplicationContext(),  obj.getString("message"),Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alt = new AlertDialog.Builder(NewPasswd.this);

                    alt.setMessage("비밀번호 변경을 성공적으로 완료했습니다. \n 해당 비밀번호로 로그인 해주시기 바랍니다.")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(NewPasswd.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                    AlertDialog alert = alt.create();
                    alert.setTitle("비밀번호 변경 완료");

                    alert.show();

                } else {
                    Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
