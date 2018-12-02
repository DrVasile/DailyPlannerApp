package com.example.vasile.dailyplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vasile.dailyplanner.db.TaskContract;
import com.example.vasile.dailyplanner.db.TaskDbHelper;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";

    private TaskDbHelper mHelper;
    private ListView listView;
    private TaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.taskList);
        mHelper = new TaskDbHelper(this);
        updateUI();
    }

    private void updateUI() {
        ArrayList<TaskModel> taskList = new ArrayList<TaskModel>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE, new String[]{
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COL_TASK_TITLE,
                        TaskContract.TaskEntry.COL_TASK_CONTENT,
                        TaskContract.TaskEntry.COL_TASK_DATE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            String currentTitle = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE));
            String currentContent = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_CONTENT));
            String currentDate = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE));
            taskList.add(new TaskModel(currentTitle, currentContent, currentDate));
        }

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(this, taskList);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
