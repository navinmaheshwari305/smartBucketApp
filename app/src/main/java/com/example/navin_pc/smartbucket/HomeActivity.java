package com.example.navin_pc.smartbucket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    public static HashMap<String , Item> allItems = new HashMap<String,Item>();
    public  static String serverUrl = "http://192.168.1.5:8080/";

    private TextView mTextMessage;
    Button scanBtn , addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new GetAllItemsAsyncTask(HomeActivity.this).execute();
        mTextMessage = (TextView) findViewById(R.id.messageHome);

        scanBtn = (Button) findViewById(R.id.scanItemButton);
        addBtn = (Button) findViewById(R.id.addItem);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( HomeActivity.this,
                        ItemListActivity.class);
                startActivity(myIntent);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( HomeActivity.this,
                        AddItemActivity.class);
                startActivity(myIntent);
            }
        });



    }

}
