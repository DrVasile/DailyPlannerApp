package com.example.vasile.dailyplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskModel> {

    private Context context;
    private List<TaskModel> taskList = new ArrayList<>();

    public TaskAdapter(Context myContext, ArrayList<TaskModel> myList) {
        super(myContext, 0, myList);
        context = myContext;
        taskList = myList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        }

        TaskModel currentTask = taskList.get(position);
        TextView title = (TextView) item.findViewById(R.id.task_title);
        title.setText(currentTask.getTitle());
        TextView content = (TextView) item.findViewById(R.id.task_content);
        content.setText(currentTask.getContent());
        TextView time = (TextView) item.findViewById(R.id.task_time);
        time.setText(currentTask.getDate());

        return item;
    }
}
