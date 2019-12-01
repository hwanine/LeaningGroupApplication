package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CheckAttendingActivity extends AppCompatActivity { //참여자 정보를 정의하는 클래스

    public String Name;
    public String SchoolNumber;
    public String EMail;
    public View.OnClickListener ReportOn;

    CheckAttendingActivity(String Name, String SchoolNumber, String EMail, View.OnClickListener ReportOn){

        this.Name = Name;
        this.SchoolNumber = SchoolNumber;
        this.EMail = EMail;
        this.ReportOn = ReportOn;
    }

}
