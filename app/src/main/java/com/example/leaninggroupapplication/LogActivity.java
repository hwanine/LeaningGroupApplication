package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcheck_list);
        ArrayList<GroupList> data = new ArrayList<>();
        data.add(new GroupList("1", "토익구함", "외국어", "2019. 11. 19", "13:30"));

        GroupAdapter adapter = new GroupAdapter(data);
        ListView listview =  (ListView)findViewById(R.id.grouplog_view);
        listview.setAdapter(adapter);
    }
}