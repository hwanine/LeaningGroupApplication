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

                //Log.e("here",s);
                JSONObject obj = new JSONObject(s);

                if (!obj.getBoolean("error")) {

                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject userJson = obj.getJSONObject("user");

                    //System.out.println(userJson);

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

