package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.println;

public class LogActivity extends AppCompatActivity {

    ArrayList<GroupList> data;
    GroupAdapter adapter;
    String category;
    String title;
    String num;
    String date;
    String starttime;
    String endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcheck_list);
        data = new ArrayList<>();
        adapter = new GroupAdapter(data);
        ListView listview =  (ListView)findViewById(R.id.grouplog_view);
        listview.setAdapter(adapter);
        new BackgroundTask().execute();



    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        protected  void onPreExecute(){
            target = "http://rkdlem1613.dothome.co.kr/getlog2.php";

            //InsertNic insert = new InsertNic(target, nic,);
        }

        @Override
                protected String doInBackground(Void... Voids) {
                    try{
                        URL url = new URL(target);
                        Intent intent = getIntent();
                        String nic = intent.getStringExtra("nic");
                        String postParameters = "nic=" + nic;

                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.connect();

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        try {
                            outputStream.write(postParameters.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
//
                public void onPostExecute(String result){
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray =jsonObject.getJSONArray("response");
                int count = 0;
                String category;
                String title;
                String num;
                String date;
                String starttime;
                String endtime;
                System.out.println(jsonArray.length());
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    num = object.getString("group_roomnumber");
                    category = object.getString("category");
                    title = object.getString("group_room_name");
                    date = object.getString("meeting_date");
                    starttime = object.getString("meeting_start_time");
                    endtime = object.getString("meeting_end_time");
                    GroupList group = new GroupList(num, title, category, date, starttime, endtime);
                    data.add(group);
                    adapter.notifyDataSetChanged();
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class InsertNic extends StringRequest {

        private Map<String, String> parameters;

        public InsertNic(String target, Response.Listener<String> listener){
            super(Method.POST, target, listener, null);
            //parameters = new HashMap<>();
            //parameters.put("nic", nic);
        }

        public Map<String, String> getParams(){
            return parameters;
        }
    }
}