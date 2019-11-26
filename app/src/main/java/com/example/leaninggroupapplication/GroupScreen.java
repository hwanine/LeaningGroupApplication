package com.example.leaninggroupapplication;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
<<<<<<< Updated upstream
        Button gs_joinBtn;
        Button sendFileBtn;
        Button gs_commentBtn;
        TextView gs_category;
        TextView gs_title;
        TextView gs_writer;
        TextView gs_content;
        TextView gs_numberOfUserMax;
        TextView gs_numberOfUserNow;
        TextView gs_date;
        TextView gs_start_time;
        TextView gs_end_time;
        EditText gs_enterComments;

        =======*/
public class GroupScreen extends AppCompatActivity {
    EditText enterCommentsEdit;
    public final int REQUEST_CODE = 101;
    private ListView listView;
    CommentsList adapter;
    private ArrayAdapter<String> listAdapter;
//>>>>>>> Stashed changes
    //댓글 테스트
    ArrayList< Comments> items = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_screen);

        Button commentsButton;
        Button fileSendButton;


        listView = findViewById(R.id.gs_commentList);
        //ArrayList< Comments> items = new ArrayList<>();
        adapter = new CommentsList(items, getApplicationContext());
        listView.setAdapter(adapter);


        Intent gIntent = getIntent();
        final String comment_nickname=gIntent.getStringExtra("inputNickname");
        String group_room_number=gIntent.getStringExtra("group_number");
        commentsButton=(Button) findViewById(R.id.gs_commentBtn);
        commentsButton.setOnClickListener(new View.OnClickListener() { //댓글 쓰고 확인 버튼 누를때
            @Override
            public void onClick(View view) {

                enterCommentsEdit = (EditText) findViewById(R.id.gs_enterComments); //댓글 내용 가져오기
                String enterCommentString = enterCommentsEdit.getText().toString();  // 댓글 내용 스트링변환
                if (enterCommentString.length() == 0) //아무것도 안썻을때
                    Toast.makeText(getApplicationContext(), "comment를 입력해주세요", Toast.LENGTH_LONG).show();
                //else if (!isNetWork()) {
                //        Toast.makeText(getApplicationContext(), "네트워크 연결 불량", Toast.LENGTH_LONG).show();

                CommentCommuincate taskComment = new CommentCommuincate();
                taskComment.execute("http://rkdlem1613.dothome.co.kr/comment.php",comment_nickname ,enterCommentString,"1"); // groupNumber은 구현후 들어가도록 하겠다

                adapter = new CommentsList(items, getApplicationContext());
                listView.setAdapter(adapter);
                items.clear();
            }
        });

        fileSendButton=(Button) findViewById(R.id.sendFileBtn);
        fileSendButton.setOnClickListener(new View.OnClickListener() { // 첨부파일 이미지 클릭할때
            @Override
            public void onClick(View view) {
                //Intent listIntent = new Intent(getApplicationContext(), FileSendActivity.class); //파일샌드액티비티에서 보낼 파일 선택하기 위해 새 액티비티 열기
                //startActivityForResult(listIntent, REQUEST_CODE);

            }
        });

    }

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
    public void comment_extraction(String response)  {
        JSONObject area1;

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray =jsonObject.getJSONArray("response");
            int count = 0;
            String commet_mem;
            String comment_cont;
            String comment_timeout;
            String group_number;

            System.out.println(jsonArray.length());
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                commet_mem = object.getString("comment_member");
                comment_cont = object.getString("comment_content");
                comment_timeout = object.getString("comment_time");
                //group_number = object.getString("group_number");

                Comments commentData = new Comments(commet_mem,comment_cont, comment_timeout);
                items.add(commentData);
                count++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CommentCommuincate extends AsyncTask<String,String,String> {
        SimpleDateFormat format1 = new SimpleDateFormat (  "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();

        @Override
        protected String doInBackground(String... params) {
            String comment_member = params[1];
            String comment_content = params[2];
            String groupRoomNumber = params[3];
            String output = "";
            String commentTime = format1.format(time);
            String postParameters = "comment_member=" + comment_member + "&comment_content=" + comment_content + "&group_roomnumber=" + groupRoomNumber
                    +"&comment_time"+commentTime;

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
                    // Log.d("됬나","성공?");

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

                    comment_extraction(output); //json 반환받는곳

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

                //Log.e("here",s);
                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {

                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject userJson = obj.getJSONObject("comment");

                    //System.out.println(userJson);


                    String comment_member=userJson.getString("comment_member");
                    String comment_content=userJson.getString("comment_content");



                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Log.e("here",s);
                    Toast.makeText(getApplicationContext(), "Invalid email or password retry again", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



}
