package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

        prePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindEmail.this, LoginActivity.class);
                startActivity(intent);

            }
        });
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

                    //파싱형태를 바꿔야겠음
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject userJson = obj.getJSONObject("user");

                    //System.out.println(userJson);

                    User user = new User(
                            userJson.getString("email"),
                            userJson.getString("nickname")
                    );


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

            return requestHandler.sendPostRequest(URLS.URL_LOGIN, params);
        }
    }
}
