package com.ucsbapp.phillip.affix;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ucsbapp.phillip.affix.Mail.SendMail;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Phillip on 8/7/2016.
 */



public class BackgroundTask  extends AsyncTask<String,Void,String>{
   Context ctx;
    BackgroundTask(Context ctx){
        this.ctx = ctx;
    }

    String email = "";
    String response = "";
    String code = "";

    @Override
    protected  void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... params){
        //This string tells which function to execute
        String method = params[0];

        String reg_url = "http://sbaffix.eu.pn/RegisterStudentApp.php";
        if(method.equals("register")){
            String fname = params[1];
            String lname = params[2];
            String uname = params[3];
            String umail = params[4];
            String major = params[5];
            String resid = params[6];
            String pswd = params[7];
            String phone = params[8];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("FirstName","UTF-8") + "=" +URLEncoder.encode(fname,"UTF-8") + "&"+
                        URLEncoder.encode("LastName","UTF-8") + "=" +URLEncoder.encode(lname,"UTF-8") + "&"+
                        URLEncoder.encode("UserName","UTF-8") + "=" +URLEncoder.encode(uname,"UTF-8") + "&"+
                        URLEncoder.encode("Umail","UTF-8") + "=" +URLEncoder.encode(umail,"UTF-8") + "&"+
                        URLEncoder.encode("Major","UTF-8") + "=" +URLEncoder.encode(major,"UTF-8") + "&"+
                        URLEncoder.encode("CurrentResidence","UTF-8") + "=" +URLEncoder.encode(resid,"UTF-8") + "&"+
                        URLEncoder.encode("Password","UTF-8") + "=" +URLEncoder.encode(pswd,"UTF-8") + "&"+
                        URLEncoder.encode("phonenum","UTF-8") + "=" +URLEncoder.encode(phone,"UTF-8") + "&";

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                //InputStream IS = httpURLConnection.getInputStream();
                //IS.close();

                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                while((line=reader.readLine())!=null){
                    sb.append(line +"");
                }
                line = sb.toString();

                response = line;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //------------------------------------------------------------------
        //Check if email is valid
        String emailcheckURL = "http://sbaffix.eu.pn/ValidEmailCheck.php";
        if(method.equals("EmailCheck")){
            email = params[1];
            code = params[2];
            try {
                URL url = new URL(emailcheckURL);
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.setDoInput(true);
                httpcon.setRequestMethod("GET");
                httpcon.setDoOutput(true);
                OutputStream OS = httpcon.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("Umail","UTF-8") + "=" +URLEncoder.encode(email,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();



                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
                String line;
                while((line=reader.readLine())!=null){
                    sb.append(line +"");
                }
                line = sb.toString();



                if(line.equals("Umail address already in use.")){
                    response = line;
                } else {
                    response = "sending mail";
                }

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        //------------------------------------------------------------------
        // Login user
        String login_usr = "http://sbaffix.eu.pn/LoginStudentApp.php";
        if(method.equals("Login")){
            try{
                String uname = params[1];
                String pswd  = params[2];
                URL log_url = new URL(login_usr);
                HttpURLConnection httplogin = (HttpURLConnection)log_url.openConnection();
                httplogin.setRequestMethod("POST");
                httplogin.setDoOutput(true);
                httplogin.setDoInput(true);
                OutputStream OS = httplogin.getOutputStream();
                BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("UserName","UTF-8") + "=" +URLEncoder.encode(uname,"UTF-8") + "&"
                        + URLEncoder.encode("Password","UTF-8") + "=" + URLEncoder.encode(pswd,"UTF-8");
                BW.write(data);
                BW.flush();
                BW.close();
                OS.close();

                InputStream IS = httplogin.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String result = "";
                String line = "";
                while((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                IS.close();
                httplogin.disconnect();

                response = result;


            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        return response;

    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        //send mail
        if(response.equals("sending mail")) {
            String body = "Welcome to AFFIX-UCSB." + "\n" + "Your Confirmation Code is " +
                    code + "\n" + "\n" + "Created by Phillip Fry";

            //Create mail object
            SendMail sendMail = new SendMail(ctx, email, "Welcome to AFFIX-UCSB", body);
            //Now send the mail
            sendMail.execute();
        }
        if(response.equals("Welcome Back")){
        }

    }


}
