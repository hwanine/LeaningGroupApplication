package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckAttenidngViewActivity extends AppCompatActivity implements View.OnClickListener{ //모임화면을 생성해 한눈에 볼 수 있도록 하는 뷰로, 시간이 되었을 때 참석하겠다고 하면 넘어가야하는 액티비티이다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attenidng_view);

        ListView listView = findViewById(R.id.AttendingListView);
        ArrayList<CheckAttendingActivity> attenders = new ArrayList<>();
        attenders.add(new CheckAttendingActivity("곽송이","20173040","rkdlem1613@changwon.ac.kr", this));

        CheckAttendingListAdapter adapter = new CheckAttendingListAdapter(attenders);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        View openParentView = (View)v.getParent();
        String position = (String) openParentView.getTag();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        String message = position + ": 해당 유저를 신고하였습니다.(나중에는 이름과 포지션을 매칭시켜 해당 유저 이름을 보여주며 신고했다고 하기. 그리고 신고하시겠습니까? 하며 신고사유를 적는 팝업이 필요! 다이얼로그 메시지 말고";
        dialog.setMessage(message);
        dialog.setPositiveButton("확인",null);
        dialog.setCancelable(false);
        dialog.show();
    }

}
