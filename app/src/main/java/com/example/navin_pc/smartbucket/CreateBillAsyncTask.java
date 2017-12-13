package com.example.navin_pc.smartbucket;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
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

/**
 * Created by NAVIN-PC on 12/13/2017.
 */

class CreateBillAsyncTask extends AsyncTask<Void, Void, String> {
    private Bill bill;
    CreateBillAsyncTask(Bill bill_){
        this.bill = bill_;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(Void... params) {
        Log.d("","Thread Running");
        JSONObject jsonBody;
        String requestBody;
        HttpURLConnection urlConnection = null;
        try {
            Gson gson = new Gson();
            requestBody = gson.toJson(bill);

            URL url = new URL("http://192.168.43.24:8080/bill");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            //urlConnection.setRequestProperty("Authorization", "Bearer " + );

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Access-Control-Allow-Headers", "Content-Type");
            urlConnection.setRequestProperty("Access-Control-Allow-Origin", "http://localhost:8080");
            urlConnection.connect();

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();


            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return "Item Added";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}