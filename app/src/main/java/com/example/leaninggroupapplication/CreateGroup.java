package com.example.leaninggroupapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v4.app.AppCompatActivity;

public class CreateGroup extends AppCompatActivity {
   // TextView textView;
    Button cg_OkBtn;
    EditText cg_content;
    String shared = "file";
    private Spinner category_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

        category_spinner = (Spinner)findViewById(R.id.category_spinner);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cg_content = (EditText)findViewById(R.id.cg_content);
        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        String value = sharedPreferences.getString("neyoung", "");
        cg_content.setText(value);

        //원래 메인화면으로 가야하는데 그룹스크린 확인하려고 냅둠
        cg_OkBtn = (Button)findViewById(R.id.cg_OkBtn);
        cg_OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroup.this,GroupScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String value = cg_content.getText().toString();
        editor.putString("neyoung",value);
        editor.commit();

    }

}

