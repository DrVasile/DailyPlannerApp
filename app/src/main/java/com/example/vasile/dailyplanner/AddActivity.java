package com.example.vasile.dailyplanner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vasile.dailyplanner.db.TaskContract;
import com.example.vasile.dailyplanner.db.TaskDbHelper;
import com.example.vasile.dailyplanner.notification.ScheduleClient;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private TaskDbHelper mHelper;
    private ScheduleClient scheduleClient;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        Intent incoming = getIntent();
        date = incoming.getStringExtra(MainActivity.DATE);
        String headerText = "Task for " + date;
        TextView header = (TextView) findViewById(R.id.textView3);
        header.setText(headerText);
    }

    public void submitTask(View view) {
        EditText titleEdit = (EditText) findViewById(R.id.title);
        EditText taskEdit = (EditText) findViewById(R.id.description);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        int day = Integer.parseInt(date.substring(8, 10));
        int month = Integer.parseInt(date.substring(5, 7)) - 1;
        int year = Integer.parseInt(date.substring(0, 4));
        int hour = timePicker.getHour();
        int minutes = timePicker.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minutes);
        scheduleClient.setAlarmForNotification(calendar);
        Toast.makeText(this, "Notification set for: "+ day +"/"+ month +"/"+ year + "-" + hour + ":" + minutes, Toast.LENGTH_SHORT).show();

        mHelper = new TaskDbHelper(this);
        String task = String.valueOf(taskEdit.getText());
        String title = String.valueOf(titleEdit.getText());
        /* time is in the format yyyy/MM/dd-HH:mm */
        String time =  date + "-" + String.valueOf(hour) + ":" + String.valueOf(minutes);

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, title);
        values.put(TaskContract.TaskEntry.COL_TASK_CONTENT, task);
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, time);
        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        Toast toast = Toast.makeText(getApplicationContext(), "Task submitted!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        finish();
    }

    @Override
    protected void onStop() {
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }
}