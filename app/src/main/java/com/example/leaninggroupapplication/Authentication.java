package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;

public class Authentication extends AppCompatActivity implements View.OnClickListener, Dialog.OnCancelListener {


    //전역변수들
    private  int certNumLength = 6;

    TextView timer; //시간 보여줌
    EditText auth_number;
    Button auth_button;
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

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

    //전역변수들
    Authentication au   = new Authentication();
    final String random = au.excuteGenerate();
    final String email = getIntent().getStringExtra("inputEmail"); //intent로 앞에서 put한 이메일을 받아왔다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {

    }

    public void countDownTimer(){

        timer = (TextView) findViewById(R.id.emailAuth_time_counter);
        auth_number = (EditText) findViewById(R.id.emailAuth_number);
        auth_button = (Button) findViewById(R.id.emailAuth_btn);

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //300초에서 1초마다 계속 줄어듦

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10){ //초가 10초보다 클 경우 그냥 출력한다.

                    timer.setText((emailAuthCount / 60) + ":" + (emailAuthCount - ((emailAuthCount / 60) * 60)));

                }else{ //초가 10초보다 작으면 앞에 0을 붙여 같이 출력

                    timer.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }

            @Override
            public void onFinish() { //시간이 다되면 다이얼로그 종료

            }
        }.start();
    }

    class EmailAuth extends AsyncTask<Void, Void, String> {

        final String userEmail;
        final String random;

        EmailAuth(String userEmail, String random) {

            this.userEmail = userEmail;
            this.random = random;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                //Log.e("here",s);
                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) { //에러없음. 적어도 이메일이 잘 간것!
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    new CountDownTimer(30000, 1000){ //30초 동안 1초 간격으로 onTick 메소드 호출
                        public void onTick(long millisUntillFinished){

                        }

                        @Override
                        public void onFinish() {

                        }

                    }.start();
                    //System.out.println(userJson);
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Log.e("here",s);
                    Toast.makeText(getApplicationContext(), "Invalid email or password retry again", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email", userEmail);
            params.put("random", random);

            return requestHandler.sendPostRequest(URLS.URL_AUTH, params);
        }
    }
}
