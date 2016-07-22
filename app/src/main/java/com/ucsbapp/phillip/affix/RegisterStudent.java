package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


public class RegisterStudent extends AppCompatActivity {

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep;
    String rec, subject, textMessage;

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
        reciep = (EditText) findViewById(R.id.umailpreffix);


    }


    private void sendEmail() {
        EditText edittextemail = (EditText) findViewById(R.id.umailpreffix);

        //String emailSend = (edittextemail.getText().toString() + "@umail.ucsb.edu").trim();
        String emailSend = ("phillipfry5@gmail.com").trim();
        String subject = ("Test Sub").trim();
        String body = ("Testing").trim();

    }

    private class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("philfry125@gmail.com", "fryball7");
        }
    }

    public void REGISTER(View v) throws MessagingException {
        View view = LayoutInflater.from(RegisterStudent.this).inflate(R.layout.confcoderegister, null);

        //allows us to print at bottom of the register button
        final TextView result = (TextView) findViewById(R.id.confirmation);

        //allows us to get the user input in the umail section and add the @umail part
        final EditText emailprefix = (EditText) findViewById(R.id.umailpreffix);
        String emailpretext = emailprefix.getText().toString();
        //final String email = emailpretext + "@umail.ucsb.edu";
        final String email = "phillipfry5@gmail.com";

        //MailService mailService = new MailService();
        //mailService.sendEmail();

        //Makes the popup dialog for the user to input the confirmation code
/*
        try {
            GMailSender sender = new GMailSender("philfry125@gmail.com",
                    "<fryball7>");
            sender.sendMail("This is Subject", "This is Body",
                    "philfry125@gmail.com", "phillipfry5@gmail.com");
            Toast.makeText(RegisterStudent.this, "Mail Send Successfully.....", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
*/
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterStudent.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.inputcode);

        alertBuilder.setCancelable(true).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String usertext = userInput.getText().toString();
                result.setText(email);


            }
        });
        Dialog dialog = alertBuilder.create();
        dialog.show();


    }
}