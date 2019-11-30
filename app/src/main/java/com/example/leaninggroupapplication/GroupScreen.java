package com.example.leaninggroupapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
//<<<<<<< Updated upstream
import android.widget.Spinner;
import android.widget.TextView;
//=======
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//>>>>>>> Stashed changes

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class GroupScreen extends AppCompatActivity {
    String group_num; // 알람 리시버에 전달할 그룹넘버


    EditText enterCommentsEdit;
    Button gs_joinBtn;
    Button gs_cancelBtn;
    TextView gs_category;
    TextView gs_title;
    TextView gs_date;
    TextView gs_start_time;
    TextView gs_end_time;
    TextView gs_writer;
    TextView gs_content;
    TextView gs_numberOfUserMax;
    TextView gs_numberOfUserNow;
    User user;
    public final int REQUEST_CODE = 101;
    private ListView listView;
    CommentsList adapter;
    private ArrayAdapter<String> listAdapter;

    ArrayList< Comments> items = new ArrayList<>();

    //SharedPreferences pref=getSharedPreferences("alarmPending",0);
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_screen);

        Button commentsButton;
        gs_joinBtn = (Button) findViewById(R.id.gs_joinBtn);
        gs_cancelBtn = (Button) findViewById(R.id.gs_cancelBtn);

        listView = findViewById(R.id.gs_commentList);

        final PrefManager prefManager = PrefManager.getInstance(GroupScreen.this);
        user = prefManager.getUser();

        //ArrayList< Comments> items = new ArrayList<>();
        adapter = new CommentsList(items);
        listView.setAdapter(adapter);


        Intent gIntent = getIntent();
        final String comment_nickname = gIntent.getStringExtra("nickname");
        final String group_room_number = gIntent.getStringExtra("group_number");
        Log.d("닉넴", comment_nickname);

        BackgroundUITask UItask = new BackgroundUITask();
        UItask.execute(group_room_number);

        commentsButton = (Button) findViewById(R.id.gs_commentBtn);
        commentsButton.setOnClickListener(new View.OnClickListener() { //댓글 쓰고 확인 버튼 누를때
            @Override
            public void onClick(View view) {

                items.clear();
                adapter.notifyDataSetChanged();



                enterCommentsEdit = (EditText) findViewById(R.id.gs_enterComments); //댓글 내용 가져오기
                InputFilter[] enterCommentsFilter = new InputFilter[1];
                enterCommentsFilter[0]=new InputFilter.LengthFilter(200);
                enterCommentsEdit.setFilters(enterCommentsFilter);

                String enterCommentString = enterCommentsEdit.getText().toString();  // 댓글 내용 스트링변환
                if (enterCommentString.length() == 0) {//아무것도 안썻을때
                    Toast.makeText(getApplicationContext(), "comment를 입력해주세요", Toast.LENGTH_LONG).show();
                    //else if (!isNetWork()) {
                    //        Toast.makeText(getApplicationContext(), "네트워크 연결 불량", Toast.LENGTH_LONG).show();
                } else {
                    CommentCommunicate taskComment = new CommentCommunicate();
                    taskComment.execute("http://rkdlem1613.dothome.co.kr/comment.php", comment_nickname, enterCommentString, group_room_number); // groupNumber은 구현후 들어가도록 하겠다
                    enterCommentsEdit.setText("");
                }
            }
        });


        gs_joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max = Integer.parseInt(gs_numberOfUserMax.getText().toString());
                int min = Integer.parseInt(gs_numberOfUserNow.getText().toString());
                System.out.println(max);
                if (prefManager.isLoggedIn()) {
                    if (max <= min) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupScreen.this);
                        builder.setMessage("인원 제한으로 참여 제한 됩니다.").setNegativeButton("확인", null).create().show();
                    } else {
                        String title = gs_title.getText().toString();
                        String category = gs_category.getText().toString();
                        String date = gs_date.getText().toString();
                        String starttime = gs_start_time.getText().toString();
                        String endtime = gs_end_time.getText().toString();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    //여기서 meeting_data 와 meeting_start_time 파싱

                                    JSONObject jsonResponse = new JSONObject(response);

                                    boolean success = jsonResponse.getBoolean("success");


                                    if (success) {

                                        String meeting_data = jsonResponse.getString("meeting_date");
                                        String meeting_start_time = jsonResponse.getString("meeting_start_time");

                                        //String meeting_data="2019-11-30";
                                        //String meeting_start_time="01:01:00";


                                        Log.d("왔나요", meeting_data);
                                        Log.d("왔나요2", meeting_start_time);

                                        Calendar cal = Calendar.getInstance();
                                        Calendar cal2 = Calendar.getInstance();

                                        cal.set(Calendar.YEAR, Integer.parseInt(meeting_data.substring(0, 4)));
                                        cal.set(Calendar.MONTH, Integer.parseInt(meeting_data.substring(5, 7)) - 1);

                                        System.out.println("똑바로 달" + cal.get(Calendar.MONTH));
                                        cal.set(Calendar.DATE, Integer.parseInt(meeting_data.substring(8, 10)));

                                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(meeting_start_time.substring(0, 2)));
                                        cal.set(Calendar.MINUTE, Integer.parseInt(meeting_start_time.substring(3, 5)));
                                        cal.set(Calendar.SECOND, Integer.parseInt(meeting_start_time.substring(6, 8)));
/*
                                    Date nextDate = cal.getTime();
                                    String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
                                    Log.d("문제 확인",date_text);


                                    cal2.set(Calendar.YEAR,Integer.parseInt(meeting_data.substring(0,4)));
                                    cal2.set(Calendar.MONTH,Integer.parseInt(meeting_data.substring(5,7)));
                                    cal2.set(Calendar.DATE,Integer.parseInt(meeting_data.substring(8,10)));

                                    cal2.set(Calendar.HOUR_OF_DAY,Integer.parseInt(meeting_start_time.substring(0,2)));
                                    cal2.set(Calendar.MINUTE,Integer.parseInt(meeting_start_time.substring(3,5))+1);
                                    cal2.set(Calendar.SECOND,Integer.parseInt(meeting_start_time.substring(6,8)));
*/
                                        Toast.makeText(getApplicationContext(), " 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

                                        diaryNotification(cal);
                                        diaryNotification(cal2);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupScreen.this);
                                        builder.setMessage("참여 성공.").setPositiveButton("확인", null).create().show();
                                        Intent intent = new Intent(GroupScreen.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupScreen.this);
                                        builder.setMessage("참여 실패.").setNegativeButton("확인", null).create().show();
                                        Intent intent = new Intent(GroupScreen.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Intent gIntent = getIntent();
                        //String nic=gIntent.getStringExtra("nickname");
                        joinGroup join = new joinGroup(group_room_number, category, title, comment_nickname, date, starttime, endtime, responseListener);
                        Log.i("여기", group_room_number);
                        RequestQueue queue = Volley.newRequestQueue(GroupScreen.this);
                        queue.add(join);

                    }

                } else {

                    Toast.makeText(getApplicationContext(), "로그인 후에 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gs_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefManager.isLoggedIn()) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupScreen.this);
                                    builder.setMessage("취소 성공.").setPositiveButton("확인", null).create().show();
                                    Intent intent = new Intent(GroupScreen.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupScreen.this);
                                    builder.setMessage("취소 실패.").setNegativeButton("확인", null).create().show();
                                    Intent intent = new Intent(GroupScreen.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Intent gIntent = getIntent();
                    cancelGroup cancel = new cancelGroup(comment_nickname, group_room_number, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(GroupScreen.this);
                    queue.add(cancel);


                } else {

                    Toast.makeText(getApplicationContext(), "로그인 후에 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



///oncreate
    /*
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // 내가 fileSendActivity액티비티를 종료하고 다시
            // group screen으로 돌아올때 동작하는 메소드이다
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE) { // 내가 액티비티를 불러올때 요청한 코드 101인지 조사한다

                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra("path");
                    String line = new String();
                    // 이아래부터는 받아온 경로의 파일을 전송하는 코드를 작성하면 됩니다


                }
            }
        }*/

    void diaryNotification(Calendar calendar)
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

    public void comment_extraction(String response)  {

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray =jsonObject.getJSONArray("response");
            int count = 0;
            String commet_mem;
            String comment_cont;
            String commentTime;
            SimpleDateFormat format = new SimpleDateFormat ("HH:mm:ss");


            System.out.println(jsonArray.length());
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                commet_mem = object.getString("comment_member");
                comment_cont = object.getString("comment_content");
                commentTime = object.getString("comment_time");
                //commentTime = format.format(commentTime);
                //Log.d("혹시 너때문이니",commentTime.toString());
                Comments commentData = new Comments(commet_mem,comment_cont,commentTime);
                items.add(commentData);
                count++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/////
    private class CommentCommunicate extends AsyncTask<String,String,String> {
        //SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        //Date time = new Date();

        @Override
        protected String doInBackground(String... params) {
            String comment_member = params[1];
            String comment_content = params[2];
            String groupRoomNumber = params[3];
            String output = "";

            String postParameters = "comment_member=" + comment_member + "&comment_content=" + comment_content + "&group_roomnumber=" + groupRoomNumber;

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
                    Log.d("됬나",output);



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

    //모임 정보 쿼리문
    class BackgroundUITask extends AsyncTask<String, String, String> {

        String target;

        protected void onPreExecute() {
            target = "http://rkdlem1613.dothome.co.kr/groupScreen.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String group_number = "group_number=" + params[0];
            try {

                Log.d("가져온거 ", "호출됨0");
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(group_number.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                Log.d("가져간거",group_number);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                    System.out.println(temp);
                }
                Log.d("가져온거 ", stringBuilder.toString());

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        //
        public void onPostExecute(String result) {
            Log.d("가져온거 ", "호출됨01");
            try {
                Log.d("가져온거 ", "여기1");
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                Log.d("가져온거 ", "호출됨");
                gs_category = findViewById(R.id.gs_category);
                gs_title = findViewById(R.id.gs_title);
                gs_writer = findViewById(R.id.gs_writer);
                gs_content = findViewById(R.id.gs_content);
                gs_numberOfUserMax = findViewById(R.id.gs_numberOfUserMax);
                gs_numberOfUserNow = findViewById(R.id.gs_numberOfUserNow);
                gs_date = findViewById(R.id.gs_date);
                gs_start_time = findViewById(R.id.gs_start_time);
                gs_end_time = findViewById(R.id.gs_end_time);



                JSONObject object = jsonArray.getJSONObject(count);
                group_num = object.getString("group_roomnumber");

                String category = object.getString("category");
                gs_category.setText(category);
                String title = object.getString("group_room_name");
                gs_title.setText(title);
                String date = object.getString("meeting_date");
                gs_date.setText(date);
                String startTime = object.getString("meeting_start_time");
                gs_start_time.setText(startTime);
                String endTime = object.getString("meeting_end_time");
                gs_end_time.setText(endTime);
                String writer = object.getString("writer");
                gs_writer.setText(writer);
                String content = object.getString("group_contents");
                gs_content.setText(content);
                String memberNumber = object.getString("member_number");
                gs_numberOfUserMax.setText(memberNumber);
                String pre_usernumber = object.getString("pre_usernum");
                gs_numberOfUserNow.setText(pre_usernumber);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("가져온거 ", "실패");
            }

        }

    }
//////
    class joinGroup extends StringRequest {

        final static private String URL = "http://rkdlem1613.dothome.co.kr/join.php";
        private Map<String, String> parameters;

        public joinGroup(String roomnum, String  category, String title, String nic, String date,
                         String starttime, String endtime, Response.Listener<String> listener){
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("category", category);
            parameters.put("title", title);
            parameters.put("roomnum", roomnum);
            parameters.put("nic", nic);
            parameters.put("date", date);
            parameters.put("starttime", starttime);
            parameters.put("endtime", endtime);
        }

        public Map<String, String> getParams(){
            return parameters;
        }


    }

    //취소 할때
    class cancelGroup extends StringRequest {

        final static private String URL = "http://rkdlem1613.dothome.co.kr/cancel.php";
        private Map<String, String> parameters;

        public cancelGroup(String nic, String roomnum, Response.Listener<String> listener){
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("nic", nic);
            parameters.put("roomnum", roomnum);
        }
        public Map<String, String> getParams(){
            return parameters;
        }
    }
}