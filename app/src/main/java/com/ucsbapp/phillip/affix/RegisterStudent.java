package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
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

    private static final String REGISTER_URL = "http://phillipucsbapp.byethost33.com/register.php";


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
        firstname = (EditText)findViewById(R.id.first_name);
        lastname = (EditText)findViewById(R.id.last_name);
        major = (Spinner)findViewById(R.id.MajorSpinner);
        currentres = (Spinner)findViewById(R.id.ResidenceSpinner);
        password = (EditText)findViewById(R.id.password);
        confpassword = (EditText)findViewById(R.id.confirmpassword);
        phonenum = (EditText)findViewById(R.id.phone);



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
        char one = (char) (var.nextInt(26) + 'a');
        char two = (char) (var.nextInt(26) + 'a');
        char three = (char) (var.nextInt(26) + 'a');
        int first = var.nextInt(100) + 1;
        int second = var.nextInt(100) + 1;
        final String firstnum = Integer.toString(first);
        String secondnum = Integer.toString(second);
        final String code = one + firstnum + two + secondnum + three;
        String body = "Welcome to AFFIX-UCSB." + "\n" + "Your Confirmation Code is " +
                code + "\n" + "\n" + "Created by Phillip Fry";


        //push to database
        String FirstName = firstname.getText().toString();
        String LastName = lastname.getText().toString();
        String Major = major.getSelectedItem().toString();
        String CurrentResidence = currentres.getSelectedItem().toString();
        String Password = password.getText().toString();
        String confpswd = confpassword.getText().toString();
        String phone = phonenum.getText().toString();
        //String type = "Register";
        //backgroundFunctions background = new backgroundFunctions(RegisterStudent.this);
        //background.execute(type, FirstName, LastName, email, Major, CurrentResidence, Password, phone);
        //new backgroundFunctions(this).execute(FirstName, LastName, email, Major, CurrentResidence, Password, phone);

        register(FirstName, LastName, email, Major, CurrentResidence, Password, phone);


        //Creating SendMail object
        SendMail sm = new SendMail(this, email, "Welcome to AFFIX-UCSB", body);
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

    private void register(String FirstName, String LastName, String Umail, String Major, String CurrentResidence, String Password, String phone) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterHelper ruc = new RegisterHelper();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterStudent.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("FirstNamee",params[0]);
                data.put("LastName",params[1]);
                data.put("Umail",params[2]);
                data.put("Major",params[3]);
                data.put("CurrentResidence",params[4]);
                data.put("Password",params[5]);
                data.put("phonenum",params[6]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(FirstName,LastName,Umail,Major,CurrentResidence,Password,phone);
    }
}