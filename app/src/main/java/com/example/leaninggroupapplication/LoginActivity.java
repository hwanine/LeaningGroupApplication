package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText email, passwd;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        init();

    }


    void init() {

        email = findViewById(R.id.email);
        passwd = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                userLogin();

            }
        });
    }


    private void userLogin() {

        final String userEmail = email.getText().toString();
        final String userPasswd = passwd.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {

            email.setError("Please enter email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userPasswd)) {

            passwd.setError("Please enter password");
            passwd.requestFocus();
            return;
        }

        UserLogin ul = new UserLogin(userEmail, userPasswd);
        ul.execute();
    }

    class UserLogin extends AsyncTask<Void, Void, String> {

        String userEmail, userPasswd;

        UserLogin(String userEmail, String userPasswd) {

            this.userEmail = userEmail;
            this.userPasswd = userPasswd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {

                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject userJson = obj.getJSONObject("user");

                    User user = new User(
                            userJson.getString("email"),
                            userJson.getString("nickname")
                    );

                    PrefManager.getInstance(getApplicationContext()).setUserLogin(user);

                    finish();

                    String inputEmail = user.getEmail();
                    String inputNickname = user.getNickname();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("inputEmail",inputEmail);
                    intent.putExtra("inputNickname", inputNickname);

                    startActivity(intent);

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
            params.put("passwd", userPasswd);

            return requestHandler.sendPostRequest(URLS.URL_LOGIN, params);
        }
    }

   /* View.OnClickListener loginButtonClickListener = new View.OnClickListener(){

      @Override
      public void onClick(View v ){

          String temp = "{\"email\""+":"+"\""+ email.getText().toString() + "\""+","
                  + "\"passwd\""+":" + "\"" + passwd.getText().toString() +"\""+"}";

      }
    };
*/

    public void onClick_Singin(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void onClick_FindEmail(View view) {
        Intent intent = new Intent(this, SignInActivity.class); //find email activity 만들어야함
        startActivity(intent);
    }

    public void onClick_ChangePassword(View view) {
        Intent intent = new Intent(this, SignInActivity.class); //find email activity 만들어야함
        startActivity(intent);
    }

    public void onClick_GoMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


/*
class Login extends AsyncTask<Void, Integer, Void> {

    @Override
    protected Void doInBackground(Void... unused){

        String param = "email";
        Log.e("POST",param);

        try{

            URL url = new URL("http://rkdlem1613.dothome.co.kr/login2.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            OutputStream out = connection.getOutputStream();
            out.write(param.getBytes("UTF-8"));
            out.flush();
            out.close();

            InputStream is = null;
            BufferedReader in = null;
            String data = "";

            is = connection.getInputStream();
            in = new BufferedReader(new InputStreamReader(is),8*1024);
            String line = null;
            StringBuffer buf = new StringBuffer();

            while((line = in.readLine())!=null){
                buf.append((line+"\n"));
            }

            data = buf.toString().trim();

            if(data.equals("0")){
                Log.e("RESULT","processing is success");
            }
            else{
                Log.e("RESULT","error occur!"+data);
            }

        }catch(MalformedURLException e){
            Log.e("RESULT","processing is success");
        }catch(IOException e){
            Log.e("RESULT","error occur!");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){

        super.onPostExecute(aVoid);

        Log.e("RECV DATA",data);

        if(data.)
    }

}

 */
