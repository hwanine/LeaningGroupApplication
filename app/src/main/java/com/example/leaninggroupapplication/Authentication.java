package com.example.leaninggroupapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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


    //전역변수
    private  int certNumLength = 6;
    final String random = executeGenerate();

    public static boolean personAllow = false;
    public static String email;

    //TextView timer; //시간 보여줌
    EditText auth_number;
    Button auth_button;
    CountDownTimer countDownTimer;

    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    //랜덤난수 생성
    public String executeGenerate() {

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
    protected void onCreate(Bundle savedInstanceState) {  //onCreate

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        Intent intent = getIntent();
        email = intent.getStringExtra("inputEmail");
        //final Authentication au = new Authentication(email, false);

        final AuthAttribute aa = new AuthAttribute(email, personAllow);

        EmailAuth ea = new EmailAuth(email, random); //메일을 에러없이 보내는 것 처리하는 asyncTask(이메일에 랜덤번호로)
        ea.execute();

        //edittext에 입력받은걸 버튼 누르면 가져와서 비교하고 처리 onClick
        //같으면 db에 있는 속성을 Y로 바꾸는걸 처리하는 asyncTask 있어야겠다 아니면 자바 자체에서 불린속성을 바꾸는 것

        auth_button = (Button)findViewById(R.id.emailAuth_btn);
        auth_number  = (EditText)findViewById(R.id.emailAuth_number);

        auth_button.setOnClickListener(new View.OnClickListener() { //인증버튼을 누르면

            @Override
            public void onClick(View v) {

                final String inputNum = auth_number.getText().toString().trim();

                if(TextUtils.isEmpty(inputNum)){
                    auth_number.setError("Please enter this component");
                    auth_number.requestFocus();
                    return;
                }

                if(inputNum.equals(random)){ //이메일 인증 성공이면 db에 인증했다고 흔적 남겨야함

                    Toast.makeText(getApplicationContext(), "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                    aa.personAllow = true;

                    Intent back = new Intent(Authentication.this, SignInActivity.class);
                    back.putExtra("personAllow",aa.personAllow);
                    back.putExtra("email", aa.email);

                    startActivity(back);

                }else{
                    Toast.makeText(getApplicationContext(), "이메일 인증 실패", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public String getEmail(){
        return email;
    }

    public boolean getPersonAllow(){
        return  personAllow;
    }

    class AuthAttribute{

        Authentication au = new Authentication();
        String email = au.getEmail();
        boolean personAllow = au.getPersonAllow();

        public AuthAttribute(String email, boolean personAllow){
            this.email = email;
            this.personAllow = personAllow;

        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {

        countDownTimer.cancel();
    }

    @Override
    public void onClick(View v) { //값 입력하고 기존의 random과 비교하기

    }

    /* public void countDownTimer(){ //타이머

        timer = (TextView) findViewById(R.id.emailAuth_time_counter);

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
                cancel();
            }
        }.start();
    }

*/

    class EmailAuth extends AsyncTask<Void, Void, String> { //이메일로 인증번호 보내기

        private String userEmail;
        private String random;

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

                } else {
                    Log.e("here",s);
                    Toast.makeText(getApplicationContext(), "some error occured", Toast.LENGTH_SHORT).show();
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
