package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

import static com.ucsbapp.phillip.affix.R.id.majors;


public class RegisterStudent extends AppCompatActivity {

    EditText firstname, lastname, umail, password, confpassword, phonenum;
    Spinner major, currentres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        //make keyboard only pop up when user clicks edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final int NavyBlue = Color.parseColor("#000080");
        final int LightYellow = Color.parseColor("#EEAD0E");

        // Sets the Arraylist Residence into the Spinner
        List<String> listLiving = Arrays.asList(getResources().getStringArray(R.array.LivingSpaces));
        Spinner currentResidence = (Spinner) findViewById(R.id.ResidenceSpinner);
        ArrayAdapter<String> livingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item , listLiving)
        {
            @Override
            public boolean isEnabled(int position){
                if(position == 0 || position == 2 || position == 12){
                    //disable Residence halls(1) and Apartments (11)
                    return false;
                }
                else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView v = (TextView) view;
                if(position == 0 || position == 2 || position == 12){
                    v.setTextColor(Color.WHITE);
                    v.setBackgroundColor(NavyBlue);
                }
                else{
                    v.setTextColor(Color.BLACK);
                    view.setBackgroundColor(LightYellow);
                }

                return view;
            }
        };

        livingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentResidence.setAdapter(livingAdapter);
        currentResidence.setSelection(1);

        // Sets the Array list Majors into the Spinner
        List<String> listmajor = Arrays.asList(getResources().getStringArray(R.array.Majors));
        Spinner majors = (Spinner) findViewById(R.id.MajorSpinner);
        ArrayAdapter<String> majorAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listmajor)
        {
            @Override
            public boolean isEnabled(int position){
                if(position == 0 || position == 7){
                    //disable Residence halls(1) and Apartments (11)
                    return false;
                }
                else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView v = (TextView) view;
                if(position == 0 || position == 7){
                    v.setTextColor(Color.WHITE);
                    v.setBackgroundColor(NavyBlue);
                }
                else{
                    v.setTextColor(Color.BLACK);
                    view.setBackgroundColor(LightYellow);
                }

                return view;
            }
        };
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majors.setAdapter(majorAdapter);
        majors.setSelection(1);

        //Set the email account to send the confirmation email
        firstname = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.last_name);
        major = (Spinner) findViewById(R.id.MajorSpinner);
        currentres = (Spinner) findViewById(R.id.ResidenceSpinner);
        password = (EditText) findViewById(R.id.password);
        confpassword = (EditText) findViewById(R.id.confirmpassword);
        phonenum = (EditText) findViewById(R.id.phone);
    }



    public void REGISTER(View v) throws MessagingException {

        //allows us to get the user input in the umail section and add the @umail part
        final EditText emailprefix = (EditText) findViewById(R.id.umailpreffix);
        String emailpretext = emailprefix.getText().toString();
        final String email = emailpretext + "@umail.ucsb.edu";
        //final String email = "phillip_fry@umail.ucsb.edu";

        //push to database
        final String FirstName = firstname.getText().toString();
        final String LastName = lastname.getText().toString();
        final String UserName = emailpretext;
        final String Umail = email;
        final String Major = major.getSelectedItem().toString();
        final String CurrentResidence = currentres.getSelectedItem().toString();
        final String Password = password.getText().toString();
        final String confpswd = confpassword.getText().toString();
        final String phone = phonenum.getText().toString();

        //Check that user typed in the correct password
        if (confpswd.equals(Password)) {/* do nothing*/ }
        else {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }


        //Create code for registration
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder rand = new StringBuilder();
        Random codegen = new Random();
        while (rand.length() < 8){
            int index = (int) (codegen.nextFloat() * allChars.length());
            rand.append(allChars.charAt(index));
        }


        final String code = rand.toString();


        /*       Check that email is not in use & send code         */
        String result = "";
        BackgroundTask login = new BackgroundTask(this);
        try {
            result = login.execute("EmailCheck", Umail, code).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

        if(result.equals("Umail address already in use.")){
            return;
        }












        //look at layouts confcoderegister.xml
        View view = LayoutInflater.from(RegisterStudent.this).inflate(R.layout.confcoderegister, null);

        //Now create pop up dialog to ask for the code
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterStudent.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.inputcode);   //where user puts in code from email


        alertBuilder.setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //overwrite later
            }
        });

        alertBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        final AlertDialog dialog = alertBuilder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                String usertext = userInput.getText().toString();
                if (usertext.equals(code)) {

                    String method = "register";
                    BackgroundTask backgroundTask = new BackgroundTask(RegisterStudent.this);
                    backgroundTask.execute(method, FirstName, LastName, UserName, Umail, Major, CurrentResidence, Password, phone);
                    Intent intent = new Intent(RegisterStudent.this, HomeScreen.class);
                    startActivity(intent);
                    wantToCloseDialog = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Code", Toast.LENGTH_LONG).show();
                }
                if (wantToCloseDialog)
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