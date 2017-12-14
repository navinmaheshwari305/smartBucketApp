package com.example.navin_pc.smartbucket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemListActivity extends AppCompatActivity {

    EditText userId;
    Button scanBtn , createBillBtn;
    TextView infoText;
    TableLayout tl;


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
                qrScan = new IntentIntegrator(ItemListActivity.this);
                qrScan.initiateScan();
            }
        });
        createBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bill bill = new Bill();
                bill.setItemCount(billItemList.size());

                float totalAmt = 0 , totalWeight =0;
                ArrayList<Item> arList = new ArrayList<Item>();
                for(Map.Entry<String,Item> map : billItemList.entrySet()){
                    arList.add(map.getValue());
                    totalAmt += map.getValue().getRate()*map.getValue().getQuantity();
                    totalWeight += map.getValue().getWeight()*map.getValue().getQuantity();
                }

                bill.setItemList(arList);
                bill.setTotal(totalAmt);
                bill.setTotalWeight(totalWeight);
                Log.d("NAVIN","userid is : "+userId.getText().toString());
                bill.setUserId(userId.getText().toString());
                new CreateBillAsyncTask(bill,ItemListActivity.this).execute();

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
                Toast.makeText(this, "Barcode is : " + result.getContents(), Toast.LENGTH_LONG).show();
                Item item = billItemList.get(result.getContents());
                if (item == null) {
                    item = HomeActivity.allItems.get(result.getContents());
                }
                else{
                    billItemList.remove(result.getContents());
                }
                item.setQuantity(item.getQuantity()+1);

                billItemList.put(item.getBarcode(),item);
                tl.removeAllViewsInLayout();
                addHeader();
                addData();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void addHeader(){
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView rowHeadSno = new TextView(this);
        rowHeadSno.setText("S No.");
        rowHeadSno.setPadding(10, 0, 0, 0);
        rowHeadSno.setTypeface(Typeface.DEFAULT ,  Typeface.BOLD);
        tr.addView(rowHeadSno);

        TextView rowHeadText1 = new TextView(this);
        rowHeadText1.setText("Name");
        rowHeadText1.setWidth(200);
        rowHeadText1.setPadding(10, 0, 0, 0);
        rowHeadText1.setTypeface(Typeface.DEFAULT ,  Typeface.BOLD);
        tr.addView(rowHeadText1);

        TextView rowHeadText2 = new TextView(this);
        rowHeadText2.setText("Rate");
        rowHeadText2.setTypeface(Typeface.DEFAULT ,  Typeface.BOLD);
        rowHeadText2.setPadding(40, 0, 0, 0);
        tr.addView(rowHeadText2);

        TextView rowHeadText3 = new TextView(this);
        rowHeadText3.setText("Quantity");
        rowHeadText3.setTypeface(Typeface.DEFAULT ,  Typeface.BOLD);
        rowHeadText3.setPadding(40, 0, 0, 0);
        tr.addView(rowHeadText3);

        TextView rowHeadText4 = new TextView(this);
        rowHeadText4.setText("Amount");
        rowHeadText4.setTypeface(Typeface.DEFAULT ,  Typeface.BOLD);
        rowHeadText4.setPadding(40, 0, 0, 0);
        tr.addView(rowHeadText4);


        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    public void addData(){

            /** Creating a TextView to add to the row **/
            float amount = 0;
            float weight= 0 ;
            int i=1;
            for(String str : billItemList.keySet()) {

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                Item item = billItemList.get(str);
                Log.d("NAVIN", "addData: "+item.getName());
                TextView rowSno = new TextView(this);
                rowSno.setText(""+i++);
                rowSno.setTypeface(Typeface.DEFAULT);
                rowSno.setPadding(10, 0, 0, 0);
                tr.addView(rowSno);  // Adding textView to tablerow.

                TextView rowText1 = new TextView(this);
                rowText1.setText(item.getName());
                rowText1.setTypeface(Typeface.DEFAULT);
                rowText1.setWidth(200);
                rowText1.setPadding(10, 0, 0, 0);
                tr.addView(rowText1);  // Adding textView to tablerow.

                TextView rowText2 = new TextView(this);
                rowText2.setText(""+item.getRate());
                rowText2.setTypeface(Typeface.DEFAULT);
                rowText2.setPadding(40, 0, 0, 0);
                tr.addView(rowText2);  // Adding textView to tablerow.

                TextView rowText3 = new TextView(this);
                rowText3.setText(""+item.getQuantity());
                rowText3.setTypeface(Typeface.DEFAULT);
                rowText3.setPadding(40, 0, 0, 0);
                tr.addView(rowText3);  // Adding textView to tablerow.

                TextView rowText4 = new TextView(this);
                rowText4.setText(""+(item.getQuantity()*item.getRate()));
                amount += (item.getQuantity()*item.getRate());
                weight += (item.getQuantity()*item.getWeight());
                rowText4.setTypeface(Typeface.DEFAULT);
                rowText4.setPadding(40, 0, 0, 0);
                tr.addView(rowText4);  // Adding textView to tablerow.

                tl.addView(tr, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
            // Add the TableRow to the TableLayout


            infoText.setText("Total Amount: Rs."+amount);

    }


}


