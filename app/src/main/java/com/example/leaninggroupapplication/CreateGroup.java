
package com.example.leaninggroupapplication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import android.support.v4.app.AppCompatActivity;

public class CreateGroup extends AppCompatActivity {

    Button cg_cancelBtn;
    Button cg_OkBtn;
    Button cg_dateBtn;
    Button cg_start_timeBtn;
    Button cg_end_timeBtn;
    Button cg_set1;
    Button cg_set2;
    Spinner category_spinner;


    EditText cg_title;
    EditText cg_content;
    EditText cg_numberOfUser;
    TextView cg_date;
    String category;
    TextView cg_start_time;
    TextView cg_end_time;

    int y=0,m=0,d=0;
    int sh=0,smi=0,eh=0,emi=0;


    TextView cg_writer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);


        category_spinner = (Spinner) findViewById(R.id.category_spinner);

        cg_content = (EditText) findViewById(R.id.cg_content);
        InputFilter[] contentFilter = new InputFilter[1];
        contentFilter[0]=new InputFilter.LengthFilter(1000);
        cg_content.setFilters(contentFilter);

        cg_title = (EditText) findViewById(R.id.cg_title);
        InputFilter[] titleFilter = new InputFilter[1];
        titleFilter[0]=new InputFilter.LengthFilter(50);
        cg_title.setFilters(titleFilter);

        cg_numberOfUser = (EditText) findViewById(R.id.cg_numberOfUser);
        InputFilter[] numberOfUserFilter = new InputFilter[1];
        numberOfUserFilter[0]=new InputFilter.LengthFilter(5);
        cg_numberOfUser.setFilters(numberOfUserFilter);

        cg_writer = (TextView) findViewById(R.id.cg_writer);
        cg_dateBtn = (Button) findViewById(R.id.cg_dateBtn);
        cg_date = (TextView) findViewById(R.id.cg_date);
        cg_start_timeBtn = (Button)findViewById(R.id.cg_start_timeBtn);
        cg_end_timeBtn = (Button)findViewById(R.id.cg_end_timeBtn);
        cg_start_time = (TextView) findViewById(R.id.cg_start_time);
        cg_end_time = (TextView) findViewById(R.id.cg_end_time);
        cg_set1 = (Button)findViewById(R.id.cg_set1);
        cg_set2 = (Button)findViewById(R.id.cg_set2);

        cg_set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cg_date.setText(y+"."+m+"."+d);
            }
        });

        cg_set2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cg_start_time.setText(sh+":"+smi);
                cg_end_time.setText(eh+":"+emi);
            }
        });




        cg_dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();

            }

        });


        cg_start_timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTime();

            }
        });

        cg_end_timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndTime();

            }

        });


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
                final String date = cg_date.getText().toString();
                String writer = cg_writer.getText().toString();
                final String starttime=cg_start_time.getText().toString();

                String endtime=cg_end_time.getText().toString();


                emptycheak(title, content, date, numberOfUser);
                if(cg_numberOfUser.getText().toString().equals("0")){
                    Toast.makeText(getApplicationContext(), "인원수는 최소 1명이여야 합니다.", Toast.LENGTH_SHORT).show();
                }

               Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                Calendar cal = Calendar.getInstance();
                                String substHour;
                                String substMinute;
                                String substMonth;
                                String substDate;

                                String group_num = jsonResponse.getString("max_group_num");

                                Log.d("날짜",date);
                                cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));

                                //1~9월까지
                                if(date.indexOf(".",5)==6) {
                                    if(date.length()==8){
                                        substMonth=date.substring(5,6);
                                        substDate=date.substring(7,8);
                                    }else{
                                        substMonth=date.substring(5,6);
                                        substDate=date.substring(7,9);
                                    }
                                } else{ // 10~12월까지
                                    if(date.length()==9){
                                        substMonth=date.substring(5,7);
                                        substDate=date.substring(8,9);
                                    }else{
                                        substMonth=date.substring(5,7);
                                        substDate=date.substring(7,10);
                                    }
                                }
                                cal.set(Calendar.MONTH, Integer.parseInt(substMonth)-1);
                                cal.set(Calendar.DATE, Integer.parseInt(substDate));

                                if(starttime.indexOf(":")==1){ // 시간이 0~10시 사이일때
                                    substHour = starttime.substring(0, 1);
                                    if(starttime.length()==3){ //  0~10분 사이
                                        substMinute=starttime.substring(2,3);
                                    }else if(starttime.length()==4){ // 10~60분 사이
                                        substMinute=starttime.substring(2,4);
                                    }else{
                                        substMinute="00";
                                    }
                                }else {
                                    substHour = starttime.substring(0, 2);
                                    if(starttime.length()==4){ // 10~12시 사이 0~10분 사이
                                        substMinute=starttime.substring(3,4);
                                    }else if(starttime.length()==5){ //10~12시 사이 10~60분 사이
                                        substMinute=starttime.substring(3,5);
                                    }else{
                                        substMinute="00";
                                    }
                                }
                                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(substHour));
                                cal.set(Calendar.MINUTE, Integer.parseInt(substMinute));
                                cal.set(Calendar.SECOND, 0);

                                diaryNotification(cal,group_num);

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                                builder.setMessage("모임 등록에 성공했습니다.").setPositiveButton("확인", null).create().show();
                                Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                                startActivity(intent);
                            } else {

                                //AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                                //builder.setMessage("모임 등록에 실패했습니다.").setNegativeButton("확인", null).create().show();
                                //Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                                //startActivity(intent);
                            }
                        } catch (JSONException e) {
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

        final PrefManager prefManager = PrefManager.getInstance(CreateGroup.this);
        User user = prefManager.getUser();

        if (prefManager.isLoggedIn()) {
            cg_writer.setText(String.valueOf(user.getEmail()));
        }


    }
    // oncreate끝


    void diaryNotification(Calendar calendar,String group_num)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        //ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class); //componentName이 어떻게 쓰이는건지 좀 봐야겠는데
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("group_num",group_num); //알람을 하려는 모임방 번호 전달

        Date currentDateTime = calendar.getTime(); // 캘린더를 date객체로 변환

        String date_text = new SimpleDateFormat("MMddhhmm").format(currentDateTime);
        Log.d("시간확인",date_text);
        int alarmTime = Integer.parseInt(date_text);
        System.out.println("알람시간"+alarmTime);
        System.out.println("똑바로 들어가나 메소드 달"+calendar.get(Calendar.MONTH));
        System.out.println("똑바로 들어가나 "+calendar.get(Calendar.DATE));

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this,
                                alarmTime,alarmIntent,PendingIntent.FLAG_ONE_SHOT)); // 이 코드가 calendar가 저장한 시간에 alarmReceiver에  인텐트를 보내어 알림바(notification)을 내리게 하는 코드이다
                //시간이랑 pendingIntent만 다르면 알람을 여러개 만들수 있음

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendings.get(alarmPendings.size()-1));
                }
            }
        /*
            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
           */
        }
        //pendingIntent = null;
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }


    class InsertData extends StringRequest {

        final static private String URL = "http://ec2-13-209-49-31.ap-northeast-2.compute.amazonaws.com/insert6.php";
        private Map<String, String> parameters;

        public InsertData(String category, String title, String content, String numberOfUser, String date,
                          String starttime, String endtime, String writer, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("category", category);
            parameters.put("title", title);
            parameters.put("content", content);
            parameters.put("numberOfUser", numberOfUser);
            parameters.put("date", date);
            parameters.put("starttime", starttime);



            parameters.put("endtime", endtime);
            parameters.put("writer", writer);
        }

        public Map<String, String> getParams() {
            return parameters;
        }
    }
    private void emptycheak(String title, String content,String date,String numberOfUser) {

        final String titlee = cg_title.getText().toString().trim();
        final String contentt = cg_content.getText().toString().trim();
        final String datee = cg_date.getText().toString().trim();
        final String numberOfUserr = cg_numberOfUser.getText().toString().trim();


        if (TextUtils.isEmpty(titlee)) {

            cg_title.setError("Please enter this component");
            cg_title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contentt)) {

            cg_content.setError("Please enter this component");
            cg_content.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(datee)) {

            cg_date.setError("Please enter this component");
            cg_date.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(numberOfUserr)) {

            cg_numberOfUser.setError("Please enter this component");
            cg_numberOfUser.requestFocus();
            return;
        }

    }
    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month + 1;
                d = dayOfMonth;

            }
        }, 2019, 11, 1);

        datePickerDialog.setMessage("모임 날짜를 선택하세요");
        datePickerDialog.show();


    }


    void showStartTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                sh = hourOfDay;
                smi = minute;
            }
        },0,0,false);

        timePickerDialog.setMessage("시간을 선택하세요");
        timePickerDialog.show();


    }

    void showEndTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eh = hourOfDay;
                emi = minute;
            }
        },0,0,false);

        timePickerDialog.setMessage("시간을 선택하세요");
        timePickerDialog.show();

    }

}


