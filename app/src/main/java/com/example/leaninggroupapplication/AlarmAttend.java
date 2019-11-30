package com.example.leaninggroupapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmAttend extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_attend);

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("참석 확인");
        builder.setMessage("모임에 참석하시겠습니까?.");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent moimIntent = new Intent(getApplicationContext(),CheckAttendingViewActivity.class);
                startActivity(moimIntent);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();



    }
}

