package com.example.navin_pc.smartbucket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.List;

public class ItemList extends AppCompatActivity {

    EditText userId;
    Button scanBtn , createBillBtn;
    TextView infoText;
    TableLayout tl;
    TableRow tr;

    //
    HashMap<String,Item> billItemList =new HashMap<String,Item>();


    static final String SCAN = "com.google.zxing.client.android";
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        scanBtn = (Button) findViewById(R.id.scanButton);
        createBillBtn = (Button) findViewById(R.id.createBillButton);
        userId = (EditText) findViewById(R.id.userIdText);
        infoText = (TextView) findViewById(R.id.infoTextItemList);
        tl = (TableLayout) findViewById(R.id.itemTable);
        addHeader();

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intializing scan object
                qrScan = new IntentIntegrator(ItemList.this);
                qrScan.initiateScan();
            }
        });
        createBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userId.setText("CreateBillbtn clicked..");
                Item myitem = billItemList.get("");
                new AddItemAsyncTask(myitem , ItemList.this).execute();
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
                Toast.makeText(this, "Barcode is : "+result.getContents(), Toast.LENGTH_LONG).show();
                Item item = new Item();
                item.setBarcode(result.getContents());
                item.setName("NAVIN MAHESHWARI");
                item.setRate(2);
                billItemList.put("NAVIN" , item);
                addData(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void addHeader(){
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView rowHeadText = new TextView(this);
        rowHeadText.setText("Barcode");
        tr.addView(rowHeadText);
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    public void addData(String barcode){

            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            TextView rowText  = new TextView(this);
            rowText.setText(barcode);
            rowText.setTextColor(Color.RED);
            rowText.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(rowText);  // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

    }


}


