package com.ucsbapp.phillip.affix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by admin on 7/29/16.
 */

public class RegisterHelper {

    public String sendPostRequest(String requestURL, HashMap<String,String> postDataParams){

        URL url;
        String response = "";
        try{
            url = new URL(requestURL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(1500);
            con.setConnectTimeout(1500);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = con.getResponseCode();

            if(responseCode == HttpsURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                response = reader.readLine();
            }
            else{
                response = "Error Registering";
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private String getPostDataString(HashMap<String,String>params)throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String,String> entry : params.entrySet()){
            if(first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
