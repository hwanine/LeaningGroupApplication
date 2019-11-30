package com.example.leaninggroupapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmAttend extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_attend);

        Intent intent=getIntent(); // alarmReceiver pendingIntent에서 날라오는 그룹넘버 데이터
        final String pass_group_num=intent.getStringExtra("group_num");
        Log.d("TTTT","msg:"+pass_group_num);

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("참석 확인");
        builder.setMessage("모임에 참석하시겠습니까?.");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent yesIntent = new Intent(getApplicationContext(),CheckAttendingViewActivity.class);
                yesIntent.putExtra("group_num",pass_group_num);
                startActivity(yesIntent);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent noIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(noIntent);
            }
        });
        builder.create();
        builder.show();



    }



}

