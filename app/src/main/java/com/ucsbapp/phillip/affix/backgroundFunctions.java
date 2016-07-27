package com.ucsbapp.phillip.affix;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

    private Context context;
    //AlertDialog alertDialog;

    public backgroundFunctions(Context ctx) {
        this.context = ctx;
    }

    String type, FirstName, LastName, Umail, Major, CurrentResidence, Password, phonenum;
    BufferedReader reader;

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        FirstName = params[0];
        LastName = params[1];
        Umail = params[2];
        Major = params[3];
        CurrentResidence = params[4];
        Password = params[5];
        phonenum = params[6];
        Log.d("Send Data Values", FirstName + LastName + Umail);

        String link;
        String data;
        BufferedReader reader;

        try {
            data = "?FirstName=" + URLEncoder.encode(FirstName, "UTF-8");
            data += "&LastName=" + URLEncoder.encode(LastName, "UTF-8");
            data += "&Umail=" + URLEncoder.encode(Umail, "UTF-8");
            data += "&Major=" + URLEncoder.encode(Major, "UTF-8");
            data += "&CurrentResidence=" + URLEncoder.encode(CurrentResidence, "UTF-8");
            data += "&Password=" + URLEncoder.encode(Password, "UTF-8");
            data += "&phonenum=" + URLEncoder.encode(phonenum, "UTF-8");

            link = "http://phillipucsbapp.byethost33.com/registrationconnect.php" + data;
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = reader.readLine();
            return result;


        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String query_result = jsonObject.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Registered.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Registration failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }

}


