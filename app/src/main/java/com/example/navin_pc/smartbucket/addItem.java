package com.example.navin_pc.smartbucket;

import android.icu.util.Output;
import android.media.midi.MidiOutputPort;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class addItem extends AppCompatActivity {
    TextView infoText;
    EditText barcodeText , nameText , rateText , weightText;
    Button submitBtn , scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //make message text field object
        barcodeText = (EditText) findViewById(R.id.barcodeText);
        nameText = (EditText) findViewById(R.id.nametext);
        rateText = (EditText) findViewById(R.id.rateText);
        weightText = (EditText) findViewById(R.id.weightText);
        //make button object
        submitBtn = (Button) findViewById(R.id.submitButton);
        scanBtn = (Button) findViewById(R.id.scanButtonAddItem);
        infoText = (TextView) findViewById(R.id.infoText);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item it = new Item();
                it.setBarcode(barcodeText.getText().toString());
                it.setName(nameText.getText().toString());
                it.setRate(Float.parseFloat(rateText.getText().toString()));
                it.setWeight(Float.parseFloat(weightText.getText().toString()));
                new AddItemAsyncTask(it,addItem.this).execute();
            }
        });
    }

    public void submit(View v){

    }

}
