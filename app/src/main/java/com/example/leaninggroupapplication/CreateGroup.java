package com.example.leaninggroupapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

//import android.support.v4.app.AppCompatActivity;

public class CreateGroup extends AppCompatActivity {
    // TextView textView;
    Button cg_OkBtn;
    Spinner category_spinner;
    EditText cg_title;
    EditText cg_content;
    EditText cg_numberOfUser;
    EditText cg_date;
    EditText cg_starttime;
    EditText cg_endtime;
    String category;


    String shared = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);


        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        cg_content = (EditText) findViewById(R.id.cg_content);
        cg_title = (EditText) findViewById(R.id.cg_title);
        cg_date = (EditText) findViewById(R.id.cg_date);
        cg_starttime = (EditText) findViewById(R.id.cg_start_time);
        cg_endtime = (EditText) findViewById(R.id.cg_end_time);

        cg_numberOfUser = (EditText) findViewById(R.id.cg_numberOfUser);
        cg_OkBtn = findViewById(R.id.cg_OkBtn);
        cg_OkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                category = "";
                String title = cg_title.getText().toString();
                String content = cg_content.getText().toString();
                String numberOfUser = cg_numberOfUser.getText().toString();
                String date = cg_date.getText().toString();
                String starttime = cg_starttime.getText().toString();
                String endtime = cg_endtime.getText().toString();


                InsertData task = new InsertData();
                task.execute("http://ljh951103.dothome.co.kr/php/insertmy.php", category, title, content, numberOfUser, date, starttime, endtime);

                Intent intent = new Intent(CreateGroup.this, GroupScreen.class);
                startActivity(intent);
            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // cg_content = (EditText) findViewById(R.id.cg_content);
        // SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        // String value = sharedPreferences.getString("neyoung", "");
        // cg_content.setText(value);

        //원래 메인화면으로 가야하는데 그룹스크린 확인하려고 냅둠
    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String value = cg_content.getText().toString();
        editor.putString("neyoung", value);
        editor.commit();

    }*/

    class InsertData extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {


            String category = (String) strings[1];
            String title = (String) strings[2];
            String content = (String) strings[3];
            String numberOfUser = (String) strings[4];
            String date = (String) strings[5];
            String starttime = (String) strings[6];
            String endtime = (String) strings[6];
            String serverURL = (String) strings[0];

            String postParameters = "category=" + category + "&title=" + title + "&content=" + content +
                    "&numberOfUser=" + numberOfUser + "&date=" + date + "&starttime=" + starttime + "&endtime=" + endtime;

            try{

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("test", "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {

                Log.d("test", "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }
}