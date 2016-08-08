package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.type;


public class RegisterStudent extends AppCompatActivity {

    private Button registerbtn;
    private EditText reciep;
    EditText firstname, lastname, umail, password, confpassword, phonenum;
    Spinner major, currentres;

    private static final String REGISTER_URL = "http://sbaffix.eu.pn.RegisterUser.php";


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
        firstname = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.last_name);
        major = (Spinner) findViewById(R.id.MajorSpinner);
        currentres = (Spinner) findViewById(R.id.ResidenceSpinner);
        password = (EditText) findViewById(R.id.password);
        confpassword = (EditText) findViewById(R.id.confirmpassword);
        phonenum = (EditText) findViewById(R.id.phone);


    }


    public void REGISTER(View v) throws MessagingException {

        //allows us to print at bottom of the register button
        final TextView result = (TextView) findViewById(R.id.confirmation);

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
        if (confpswd.equals(Password)) {

        } else {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        //Create code for registration
        Random var = new Random();
        char one = (char) (var.nextInt(26) + 'a');
        char two = (char) (var.nextInt(26) + 'a');
        char three = (char) (var.nextInt(26) + 'a');
        int first = var.nextInt(100) + 1;
        int second = var.nextInt(100) + 1;
        final String firstnum = Integer.toString(first);
        String secondnum = Integer.toString(second);
        final String code = one + firstnum + two + secondnum + three;

        /*       Check that email is not in use & send code         */
        String checkmailmethod = "EmailCheck";
        BackgroundTask checkmail = new BackgroundTask(this);
        checkmail.execute(checkmailmethod, Umail, code);

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