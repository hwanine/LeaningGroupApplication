package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class CheckAttendingActivity extends AppCompatActivity {

    public String Name;
    public String SchoolNumber;
    public String EMail;
    public Boolean ReportOn;

    CheckAttendingActivity(String Name, String SchoolNumber, String EMail, boolean ReportOn){

        this.Name = Name;
        this.SchoolNumber = SchoolNumber;
        this.EMail = EMail;
        this.ReportOn = ReportOn;
    }
}
