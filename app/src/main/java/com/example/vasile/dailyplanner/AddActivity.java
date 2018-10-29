package com.example.vasile.dailyplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent incoming = getIntent();
        String date = "Task for " + incoming.getStringExtra(MainActivity.DATE);
        TextView header = (TextView) findViewById(R.id.textView3);
        header.setText(date);
    }
}
