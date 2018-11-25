package com.example.vasile.dailyplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int SUBMIT = 1;
    public static final String TAG = "CalendarActivity";
    public static final String DATE = "Date";
    public static final String EXTRA_MESSAGE = "com.example.vasile.dailyplanner";

    public String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                date = year + "/" + month + "/" + day;
            }
        });
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra(DATE, date);
        startActivity(intent);
    }

    public void updateTask(View view) {

    }

    public void ListTasks(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void searchByKey(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, SUBMIT);
    }
}
