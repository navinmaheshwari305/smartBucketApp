package com.example.navin_pc.smartbucket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddItemActivity extends AppCompatActivity {
    TextView infoText;
    EditText barcodeText , nameText , rateText , weightText;
    Button submitBtn , scanBtn;

    static final String SCAN = "com.google.zxing.client.android";
    //qr code scanner object
    private IntentIntegrator qrScan;

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
                new AddItemAsyncTask(it,AddItemActivity.this).execute();
            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intializing scan object
                qrScan = new IntentIntegrator(AddItemActivity.this);
                qrScan.initiateScan();
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                infoText.setText(result.getContents());
                barcodeText.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
