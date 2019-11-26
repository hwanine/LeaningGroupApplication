
package com.example.leaninggroupapplication;

import android.app.AlertDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import android.support.v4.app.AppCompatActivity;

public class CreateGroup extends AppCompatActivity {

    Button cg_cancelBtn;
    Button cg_OkBtn;
    Spinner category_spinner;
    EditText cg_title;
    EditText cg_content;
    EditText cg_numberOfUser;
    EditText cg_date;
    EditText cg_starttime;
    EditText cg_endtime;
    String category;
    TextView cg_writer;



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
        cg_writer = (TextView)findViewById(R.id.cg_writer);


        cg_cancelBtn = findViewById(R.id.cg_cancelBtn);
        cg_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cg_OkBtn = findViewById(R.id.cg_OkBtn);
        cg_OkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String title = cg_title.getText().toString();
                String content = cg_content.getText().toString();
                String numberOfUser = cg_numberOfUser.getText().toString();
                String date = cg_date.getText().toString();
                String starttime = cg_starttime.getText().toString();
                String endtime = cg_endtime.getText().toString();
                String writer = cg_writer.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                                builder.setMessage("모임 등록에 성공했습니다.").setPositiveButton("확인",null).create().show();
                                Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                                builder.setMessage("모임 등록에 실패했습니다.").setNegativeButton("확인",null).create().show();
                                Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            System.out.println("EWfw");
                        }
                    }
                };
                InsertData insertData = new InsertData(category, title, content, numberOfUser, date, starttime, endtime, writer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateGroup.this);
                queue.add(insertData);

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

    }

    class InsertData extends StringRequest {

        final static private String URL = "http://rkdlem1613.dothome.co.kr/insert6.php";
        private Map<String, String> parameters;

        public InsertData(String  category, String title, String content, String numberOfUser, String date,
                          String starttime, String endtime, String writer, Response.Listener<String> listener){
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("category", category);
            parameters.put("title", title);
            parameters.put("content", content);
            parameters.put("numberOfUser", numberOfUser);
            parameters.put("date", date);
            parameters.put("starttime", starttime);
            parameters.put("endtime", endtime);
            parameters.put("writer",writer);
        }

        public Map<String, String> getParams(){
            return parameters;
        }
    }
}