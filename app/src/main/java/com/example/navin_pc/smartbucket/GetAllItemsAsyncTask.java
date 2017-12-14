package com.example.navin_pc.smartbucket;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

class GetAllItemsAsyncTask extends AsyncTask<Void, Void, String> {

    Activity activity;

    GetAllItemsAsyncTask(Activity activity){
        this.activity = activity;
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

            URL url = new URL(HomeActivity.serverUrl + "item/all");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
           // urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");

            //urlConnection.setRequestProperty("Authorization", "Bearer " + );

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Access-Control-Allow-Headers", "Content-Type");
            urlConnection.setRequestProperty("Access-Control-Allow-Origin", "http://localhost:8080");
            urlConnection.connect();

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
            Log.d("Info" , "response is:"+response.toString());
            Item[] list = gson.fromJson(response.toString() , Item[].class);
            for(Item i : list) {
                HomeActivity.allItems.put(i.getBarcode() , i);
            }
            return  "Success";
        }
        catch (MalformedURLException e) {
            Log.d("1.Error", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("2.Error", e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return "Error Occured";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(activity,"Fected all Items :"+result , Toast.LENGTH_LONG).show();
    }

}