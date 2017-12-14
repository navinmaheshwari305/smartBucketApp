package com.example.navin_pc.smartbucket;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

class CreateBillAsyncTask extends AsyncTask<Void, Void, Bill> {
    private Bill bill;
    Activity activity;
    CreateBillAsyncTask(Bill bill_ , Activity activity){
        this.bill = bill_;
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected Bill doInBackground(Void... params) {
        Log.d("","Thread Running");
        JSONObject jsonBody;
        String requestBody;
        HttpURLConnection urlConnection = null;
        try {
            Gson gson = new Gson();
            requestBody = gson.toJson(bill);
            Log.d("NAVIN :JSON is:", requestBody);

            if(bill.getUserId().isEmpty()){

                JsonObject jsonObject =  new JsonParser().parse(requestBody).getAsJsonObject();
                jsonObject.remove("userId");
                requestBody = jsonObject.toString();
            }
            Log.d("NAVIN :JSON is:", requestBody);
            URL url = new URL(HomeActivity.serverUrl + "bill");
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
            return  gson.fromJson(response , Bill.class);
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
        return new Bill();
    }

    @Override
    protected void onPostExecute(Bill result) {
        super.onPostExecute(result);
        EditText userId;
        userId = (EditText) activity.findViewById(R.id.userIdText);
        userId.setText(result.getUserId());
    }

}