package com.example.leaninggroupapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupScreen extends AppCompatActivity {

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

    //댓글 테스트
    //String userNameArray[]={"이수민","곽송이","오철원","이재환"};
    //String commentArray[]={"ㅜㅁ라ㅐㅑ덜ㄹ머ㅔㅑㄹ!!!!!!!!!!!!@@@@@@@@@3333333333444444444","ㅁㄹㄷㅈㄷㅈㄹㅁ","하하ㅣㅇㅎ","ㅏㅁ너리ㅏㄹㄹㄹ"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_screen);



       ListView listview = findViewById(R.id.gs_commentList);
        ArrayList< Comments> items = new ArrayList<>();

       /*for(int i = 0; i < 4; i++){
            items.add(new Comments(userNameArray[i],commentArray[i]));
        }*/

        CommentsList adapter = new CommentsList(items, getApplicationContext());
        listview.setAdapter(adapter);
    }


}
