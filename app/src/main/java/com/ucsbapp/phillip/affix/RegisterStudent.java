package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ucsbapp.phillip.affix.mail.GMailSender;
import com.ucsbapp.phillip.affix.mail.SMTPMail;
import com.ucsbapp.phillip.affix.mail.SendMail;
import com.ucsbapp.phillip.affix.mail.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.jar.Attributes;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RegisterStudent extends AppCompatActivity {

    private mail M;

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
        M = new mail("phillipfry5@gmail.com", "fryball7");


    }


    private void sendEmail(){
        EditText edittextemail = (EditText)findViewById(R.id.umailpreffix);

        //String emailSend = (edittextemail.getText().toString() + "@umail.ucsb.edu").trim();
        String emailSend = ("phillipfry5@gmail.com").trim();
        String subject = ("Test Sub").trim();
        String body = ("Testing").trim();

        SendMail sm = new SendMail(this, emailSend, subject, body);

        sm.execute();
    }



    public void REGISTER(View v) {
        View view = LayoutInflater.from(RegisterStudent.this).inflate(R.layout.confcoderegister, null);

        //allows us to print at bottom of the register button
        final TextView result = (TextView) findViewById(R.id.confirmation);

        //allows us to get the user input in the umail section and add the @umail part
        final EditText emailprefix = (EditText) findViewById(R.id.umailpreffix);
        String emailpretext = emailprefix.getText().toString();
        final String email = emailpretext + "@umail.ucsb.edu";

        sendEmail();
    }



        /*
        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("philfry125@gmail.com", "fryball7");
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress("philfry125@gmail.com"));
            message.addRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("phillipfry5@gmail.com"));
            message.setSubject("Test");
            message.setContent("Hello to AFFIX","text/html; charset=utf-8");

            Transport transport = session.getTransport("smtps");
            transport.connect("philfry125@gmail.com", "Phillip Fry", "fryball7");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            //Transport.connect();
            //Transport.send(message);
            //Transport.close();
        }
        catch (MessagingException e){
            throw new RuntimeException(e);
        }
*/

    //mail M = new mail("philfry125@gmail.com", "fryball7");
/*
        GMailSender sender = new GMailSender("philfry125@gmail.com","fryball7");
        try {
            sender.sendMail("Email Subject", "<b>Hi</b><br/>Complete msg with html tags.", "philfry125@gmail.com", "phillipfry5@gmail.com", "phillip_fry@umail.ucsb.edu");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //thread.start();
*/
/*
        String TOArray[] = {email};
        M.setTo(TOArray);
        M.setFrom("phillipfry5@gmail.com");
        M.setSubject("Welcome to AFFIX-UCSB");
        M.setBody("You are the first user to receive an email. App created by Phillip Fry");


        try {
            M.send();
            if (M.send()) {
                // success
                Toast.makeText(RegisterStudent.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
            } else {
                // failure
                Toast.makeText(RegisterStudent.this, "Email was not sent.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // some other problem
            Toast.makeText(RegisterStudent.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
        }
*/

    //Makes the popup dialog for the user to input the confirmation code
        /*
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterStudent.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText)view.findViewById(R.id.inputcode);

        alertBuilder.setCancelable(true).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String usertext = userInput.getText().toString();
                result.setText(email);


            }
        });
        Dialog dialog = alertBuilder.create();
        dialog.show();
        */

    /*

    EditText firstname, lastname, umail, phoneNUM, password, confpassword;
    Spinner major, residence;
    String Firstname, Lastname, Umail, PhoneNUM, Password, Confpassword, Major, Residence;
    Context ctx = this;

    public void REGISTER(View v){
        firstname = (EditText)findViewById(R.id.first_name);
        lastname = (EditText)findViewById(R.id.last_name);
        umail = (EditText)findViewById(R.id.umailpreffix);
        phoneNUM = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.password);
        confpassword = (EditText)findViewById(R.id.confirmpassword);
        major = (Spinner)findViewById(R.id.MajorSpinner);
        residence = (Spinner)findViewById(R.id.ResidenceSpinner);

        Firstname = firstname.getText().toString();
        Lastname = lastname.getText().toString();
        Umail = umail.getText().toString() + "@umail.ucsb.edu";
        PhoneNUM = phoneNUM.getText().toString();
        Password = password.getText().toString();
        Confpassword = confpassword.getText().toString();
        Major = major.getSelectedItem().toString();
        Residence = residence.getSelectedItem().toString();

        Background b = new Background();
        b.execute();

    }
    */
}


