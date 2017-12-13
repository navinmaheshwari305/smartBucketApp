package com.example.navin_pc.smartbucket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {

    public static HashMap<String , Item> allItems = new HashMap<String,Item>();

    private TextView mTextMessage;
    Button scanBtn , addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);

        scanBtn = (Button) findViewById(R.id.scanItemButton);
        addBtn = (Button) findViewById(R.id.addItem);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( Home.this,
                        ItemList.class);
                startActivity(myIntent);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent( Home.this,
                        addItem.class);
                startActivity(myIntent);
            }
        });
    }

}
