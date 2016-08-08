package com.ucsbapp.phillip.affix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginStudent extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

    }

 public void GoToRegistration(View v){
     Intent intent = new Intent(this, RegisterStudent.class);
     startActivity(intent);
 }

    public void LoginUser(View view){
        String uname = username.getText().toString();
        String pswd = password.getText().toString();

        BackgroundTask login = new BackgroundTask(this);
        login.execute("Login",uname,pswd);
    }


}
