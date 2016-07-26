package com.ucsbapp.phillip.affix;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

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

/**
 * Created by admin on 7/26/16.
 */
//String, Void, String

 public class backgroundFunctions  extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;

    backgroundFunctions(Context ctx) {
        context = ctx;
    }

    String type, FirstName, LastName, Umail, Major, CurrentResidence, Password, phonenum;
    BufferedReader reader;

    @Override
    protected String doInBackground(String... params) {
        FirstName = params[1];
        LastName = params[2];
        Umail = params[3];
        Major = params[4];
        CurrentResidence = params[5];
        Password = params[6];
        phonenum = params[7];
        Log.d("Send Data Values", FirstName + LastName + Umail);

        try {
            String data = URLEncoder.encode("FirstName", "UTF-8") + "=" + URLEncoder.encode(FirstName, "UTF-8");
            data += "&" + URLEncoder.encode("LastName", "UTF-8") + "=" + URLEncoder.encode(LastName, "UTF-8");
            data += "&" + URLEncoder.encode("Umail", "UTF-8") + "=" + URLEncoder.encode(Umail, "UTF-8");
            data += "&" + URLEncoder.encode("Major", "UTF-8") + "=" + URLEncoder.encode(Major, "UTF-8");
            data += "&" + URLEncoder.encode("CurrentResidence", "UTF-8") + "=" + URLEncoder.encode(CurrentResidence, "UTF-8");
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8");
            data += "&" + URLEncoder.encode("phonenum", "UTF-8") + "=" + URLEncoder.encode(phonenum, "UTF-8");

            URL url = new URL("http://phillipucsbapp.byethost33.com/registrationconnect.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //For POST Only - Begin
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            //For POST Only End
            int responseCode = connection.getResponseCode();
            Log.d("Sending Class----", "POST RESPONSE CODE " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                //Success
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                //Print Result
                Log.d("SENDING CLASS----", response.toString());

            } else {
                Log.d("SENDING CLASS---", "POST did not work");

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}





    /*
    Context context;
    AlertDialog alertDialog;

    backgroundFunctions(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://phillipucsbapp.byethost33.com/registrationconnect.php";
        if (type.equals("Register")) {
            try{
                String FirstName = params[1];
                String LastName = params[2];
                String Umail = params[3];
                String Major = params[4];
                String CurrentResidence = params[5];
                String Password = params[6];
                String phonenum = params[7];
                URL url = new URL(register_url);

                HttpURLConnection httpURLConn = (HttpURLConnection)url.openConnection();
                httpURLConn.setRequestMethod("POST");
                httpURLConn.setDoOutput(true);
                httpURLConn.setDoInput(true);
                OutputStream outputStream = httpURLConn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("FirstName","UTF-8")+"="+URLEncoder.encode(FirstName,"UTF-8")+"&"
                        + URLEncoder.encode("LastName","UTF-8")+"="+URLEncoder.encode(LastName,"UTF-8")+"&"
                        + URLEncoder.encode("Umail","UTF-8")+"="+URLEncoder.encode(Umail,"UTF-8")+"&"
                        + URLEncoder.encode("Major","UTF-8")+"="+URLEncoder.encode(Major,"UTF-8")+"&"
                        + URLEncoder.encode("CurrentResidence","UTF-8")+"="+URLEncoder.encode(CurrentResidence,"UTF-8")+"&"
                        + URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"&"
                        + URLEncoder.encode("phonenum","UTF-8")+"="+URLEncoder.encode(phonenum,"UTF-8")+"&";

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConn.disconnect();
                return result;


            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}

*/
