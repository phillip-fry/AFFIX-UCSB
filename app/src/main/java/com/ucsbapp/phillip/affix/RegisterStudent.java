package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ucsbapp.phillip.affix.Mail.SendMail;

import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


public class RegisterStudent extends AppCompatActivity {

   private Button registerbtn;
   private EditText reciep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        // Sets the Arraylist Residence into the Spinner
        Spinner currentResidence = (Spinner) findViewById(R.id.ResidenceSpinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.LivingSpaces,
                android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentResidence.setAdapter(staticAdapter);


        // Sets the Array list Majors into the Spinner
        Spinner majors = (Spinner) findViewById(R.id.MajorSpinner);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this, R.array.Majors,
                android.R.layout.simple_spinner_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majors.setAdapter(majorAdapter);

        //Set the email account to send the confirmation email


    }



    public void REGISTER(View v) throws MessagingException {
        View view = LayoutInflater.from(RegisterStudent.this).inflate(R.layout.confcoderegister, null);

        //allows us to print at bottom of the register button
        final TextView result = (TextView) findViewById(R.id.confirmation);

        //allows us to get the user input in the umail section and add the @umail part
        final EditText emailprefix = (EditText) findViewById(R.id.umailpreffix);
        String emailpretext = emailprefix.getText().toString();
        final String email = emailpretext + "@umail.ucsb.edu";
        //final String email = "phillip_fry@umail.ucsb.edu";

        Random var = new Random();
        char one = (char)(var.nextInt(26) + 'a');
        char two = (char)(var.nextInt(26) + 'a');
        char three = (char)(var.nextInt(26) + 'a');
        int first = var.nextInt(100) + 1;
        int second = var.nextInt(100) + 1;
        String firstnum = Integer.toString(first);
        String secondnum = Integer.toString(second);
        final String code = one + firstnum + two + secondnum + three;
        String body = "Welcome to AFFIX-UCSB." + "\n" + "Your Confirmation Code is " +
                code + "\n" + "\n" + "Created by Phillip Fry";

        //Creating SendMail object
        SendMail sm = new SendMail(this,email,"Welcome to AFFIX-UCSB", body);
        //Executing sendmail to send mail
        sm.execute();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterStudent.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.inputcode);


        alertBuilder.setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Overwrite later
            }
        });

        final AlertDialog dialog = alertBuilder.create();
        dialog.show();


        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Boolean wantToCloseDialog = false;
                String usertext = userInput.getText().toString();
                if (usertext.equals(code)){
                    Intent intent = new Intent(RegisterStudent.this, HomeScreen.class);
                    startActivity(intent);
                    wantToCloseDialog = true;
                } else {
                    Toast.makeText(getApplicationContext(),"Incorrect Code",Toast.LENGTH_LONG).show();
                }
                if(wantToCloseDialog)
                    dialog.dismiss();
            }
        });


        alertBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });







    }
}