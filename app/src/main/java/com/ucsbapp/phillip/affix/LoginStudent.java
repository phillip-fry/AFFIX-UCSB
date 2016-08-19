package com.ucsbapp.phillip.affix;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class LoginStudent extends AppCompatActivity {

    EditText username;
    EditText password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        //make keyboard only pop up when user clicks edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

 public void GoToRegistration(View v){
     Intent intent = new Intent(this, RegisterStudent.class);
     startActivity(intent);

 }

    public void LoginUser(View view){
        String uname = username.getText().toString();
        String pswd = password.getText().toString();
        String result = "";
        BackgroundTask login = new BackgroundTask(this);
        try {
            result = login.execute("Login", uname, pswd).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

        if(result.equals("Welcome Back")){
            Intent intent = new Intent(LoginStudent.this, HomeScreen.class);
            intent.putExtra("username",uname);
            intent.putExtra("password",pswd);
            startActivity(intent);
        }
    }

}
