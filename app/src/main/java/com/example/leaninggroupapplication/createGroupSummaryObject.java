package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

public class createGroupSummaryObject extends AppCompatActivity { //모임 요약 정보를 정의하는 클래스

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

    public String Title() {
        return Title;
    }

    public String GroupDate() {
        return GroupDate;
    }

    public String Writer() {
        return Writer;
    }

    public String GroupNumOfMem() {
        return GroupNumOfMem;
    }

    public String GroupNum() {
        return GroupNum;
    }

    @Override
    public String toString() {
        return "Group{" +
                "Title='" + Title + '\'' +
                ", GroupDate='" + GroupDate + '\'' +
                ", Writer='" +Writer + '\'' +
                ", GroupNumOfMem='" + GroupNumOfMem + '\'' +
                ", GroupNum='" + GroupNum + '\'' +
                '}';
    }

}
