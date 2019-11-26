package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class Authentication extends AppCompatActivity implements View.OnClickListener, Dialog.OnCancelListener {

    private  int certNumLength = 6;

    public Authentication(){
        int random;
    }

    public String excuteGenerate() {

        Random random = new Random(System.currentTimeMillis());

        int range = (int) Math.pow(10, certNumLength);
        int trim = (int) Math.pow(10, certNumLength - 1);
        int result = random.nextInt(range) + trim;

        if (result > range) {
            result = result - trim;
        }

        return String.valueOf(result);
    }

    public int getCertNumLength() {
        return certNumLength;
    }

    public void setCertNumLength(int certNumLength) {
        this.certNumLength = certNumLength;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Authentication au = new Authentication();
        au.setCertNumLength(6);

        String random;
        random = au.excuteGenerate(); //랜덤난수를 생성했다. 이걸 서버로 보내야함


    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {

    }
}
