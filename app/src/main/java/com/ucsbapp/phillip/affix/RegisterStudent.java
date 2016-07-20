package com.ucsbapp.phillip.affix;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;

public class RegisterStudent extends AppCompatActivity {

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
    }

    public void REGISTER(View v){
        View view = LayoutInflater.from(RegisterStudent.this).inflate(R.layout.confcoderegister,null);

        final TextView result = (TextView)findViewById(R.id.confirmation);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterStudent.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText)findViewById(R.id.inputcode);
        alertBuilder.setCancelable(true).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                result.setText(userInput.getText());
            }
        });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }



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

    class Background extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params){
            String firstname = params[0];
            String lastname = params[1];
            String umail = params[2];
            String phoneNUM = params[3];
            String major = params[4];
            String residence = params[5];
            String password = params[6];
            String data="";
            int tmp;

            try{
                URL url = new URL("http://185.27.134.9/index.php");
                String urlParams = "First Name"+firstname+"Last Name"+lastname+"umail"+umail
                        +"phone #"+phoneNUM+"Major"+major+"Current Residence"+residence+"Password"+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+=(char)tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e){
                e.printStackTrace();
                return "Exception:" + e.getMessage();
            } catch (IOException e){
                e.printStackTrace();
                return "Exception:" + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s){
            if(s.equals("")){
                s="Data saved sucessfully.";
            }
            Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
        }


    }

*/
}
