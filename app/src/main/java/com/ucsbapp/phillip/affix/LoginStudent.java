package com.ucsbapp.phillip.affix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
    }

 public void GoToRegistration(View v){
     Intent intent = new Intent(this, RegisterStudent.class);
     startActivity(intent);
 }


}
