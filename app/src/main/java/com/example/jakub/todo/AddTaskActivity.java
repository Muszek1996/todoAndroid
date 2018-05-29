package com.example.jakub.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jakub.todo.Gson.TaskSerializer;
import com.example.jakub.todo.RecyclerView.RecycleViewAdapter;
import com.example.jakub.todo.RecyclerView.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText taskDescription;
    private Button addTaskButton;
    private DatePicker datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.todoRecyclerView);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskName = (EditText) findViewById(R.id.taskNameEditText);
        taskDescription = (EditText) findViewById(R.id.taskDescriptionEditText);
        addTaskButton = (Button) findViewById(R.id.addTaskButton);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        datePicker.updateDate((int) cal.get(Calendar.YEAR), (int) cal.get(Calendar.MONTH), (int) cal.get(Calendar.DAY_OF_MONTH));

        Task task;
        if(getIntent().getExtras()!=null){
           task  = TaskSerializer.deserialize(getIntent().getExtras().getString("TASK"));
           taskName.setText(task.getName());
           taskDescription.setText(task.getDescription());
           datePicker.updateDate(task.getDate().get(Calendar.YEAR),task.getDate().get(Calendar.MONTH),task.getDate().get(Calendar.DAY_OF_MONTH));
           addTaskButton.setText("EDIT!");
        }


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask();
            }
        });


    }

    private void editTask() {
        Task task;
        if (taskName.getText().length() == 0 || taskDescription.getText().length() == 0) {
            Toast.makeText(this, "Task title and date cannot be empty!", Toast.LENGTH_LONG).show();
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, datePicker.getYear());
            cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            cal.set(Calendar.MONTH, datePicker.getMonth());
            if(getIntent().getExtras()!=null){
                task  = TaskSerializer.deserialize(getIntent().getExtras().getString("TASK"));
            }else {
                task = new Task();
            }
            task.setName(taskName.getText().toString());
            task.setDescription(taskDescription.getText().toString());
            task.setDate(cal);
            String jsonTask = TaskSerializer.serialize(task);
            Intent intent = new Intent();
            intent.putExtra("newTask", jsonTask);
            setResult(RESULT_OK, intent);
            finish();
        }


    }

}
