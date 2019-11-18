package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

public class createGroupSummaryObject extends AppCompatActivity {

    public String Title;
    public String GroupDate;
    public String Writer;
    public String GroupNumOfMem;
    public String GroupNum;

    createGroupSummaryObject(String Title, String GroupDate, String Writer, String GroupNumOfMem, String GroupNum){

        this.Title = Title;
        this.GroupDate = GroupDate;
        this.Writer = Writer;
        this.GroupNumOfMem = GroupNumOfMem;
        this.GroupNum = GroupNum;

    }
}
