package com.example.vasile.dailyplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "CalendarActivity";
    public static final String DATE = "Date";
    public static final String EXTRA_MESSAGE = "com.example.vasile.dailyplanner";

    public String date = "1997/07/30";

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

    public void deleteTask(View view) {

    }

    public void searchByKey(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
