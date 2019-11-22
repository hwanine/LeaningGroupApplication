package com.example.leaninggroupapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, passwd;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        email = (EditText)findViewById(R.id.email);
        passwd = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final String userEmail = email.getText().toString();
                final String userPasswd = passwd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){

                                String userEmail = jsonObject.getString("email");
                                String userPasswd = jsonObject.getString("passwd");
                                String userNickname = jsonObject.getString("nickname");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userEmail",userEmail);
                                intent.putExtra("userPasswd",userPasswd);
                                intent.putExtra("userNickname",userNickname);

                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.");
                                builder.setNegativeButton("retry again", null);
                                builder.create();
                                builder.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userEmail,userPasswd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }

        });
    }

    public void onClick_Singin(View view){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void onClick_FindEmail(View view){
        Intent intent = new Intent(this, SignInActivity.class); //find email activity 만들어야함
        startActivity(intent);
    }

    public void onClick_ChangePassword(View view){
        Intent intent = new Intent(this, SignInActivity.class); //find email activity 만들어야함
        startActivity(intent);
    }

    public void onClick_GoMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

class LoginRequest extends StringRequest {

    final static private String URL = "http://rkdlem1613.dothome.co.kr/login.php";
    private Map<String, String> parameter;

    public LoginRequest(String userEmail, String userPasswd, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);
        try{

            parameter = new HashMap<>();
            parameter.put("email", userEmail);
            parameter.put("passwd",userPasswd);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, String> getParams() {
        return parameter;
    }
}
