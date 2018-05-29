package com.example.jakub.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jakub.todo.Gson.TaskSerializer;
import com.example.jakub.todo.RecyclerView.RecycleViewAdapter;
import com.example.jakub.todo.RecyclerView.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class mainTODO extends AppCompatActivity {

    static final int GET_TASK_REQUEST = 7;
    static final int EDIT_TASK_REQUEST = 8;
    private RecycleViewAdapter mAdapter;
    private List<Task> tasks;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.todoRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR , 1996);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 9);


        sharedPref = mainTODO.this.getSharedPreferences("TASKS", Context.MODE_PRIVATE);

        Map<String,?> tasksMap = new HashMap<>();
        tasksMap = sharedPref.getAll();
        List<Task> tasks = new LinkedList<>();
        for (Map.Entry<String, ?> entry : tasksMap.entrySet()) {
            tasks.add(TaskSerializer.deserialize(entry.getValue().toString()));
        }


        mAdapter = new RecycleViewAdapter(tasks,this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               handleNewTaskButtonAction();
            }
        });
    }

    private void handleNewTaskButtonAction(){
        Intent myIntent = new Intent(this,AddTaskActivity.class);
        startActivityForResult(myIntent,TASK_REQUEST.CREATE.getValue());
    }

    public void editTask(Task task){
        Intent myIntent = new Intent(this,AddTaskActivity.class);
        myIntent.putExtra("TASK",TaskSerializer.serialize(task));
        startActivityForResult(myIntent,TASK_REQUEST.EDIT.getValue());
    }

    public void removeTask(String key){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TASK_REQUEST.CREATE.getValue()||requestCode==TASK_REQUEST.EDIT.getValue()) {
            if(resultCode == RESULT_OK){
                String newTask = data.getStringExtra("newTask");
                Task task = TaskSerializer.deserialize(newTask);
                boolean flag = true;
                for(Task a:mAdapter.getTasks()){
                    if(a.getId()==task.getId()){
                        flag = false;
                    }
                }
                if(flag){
                    mAdapter.getTasks().add(task);
                }else {
                    mAdapter.getTasks().forEach(t->{
                        if(t.getId()==task.getId()){
                            t.setDate(task.getDate());
                            t.setDescription(task.getDescription());
                            t.setName(task.getName());
                        }
                    });
                }
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(String.valueOf(task.getId()),newTask);
                editor.apply();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Task created", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

      

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }
}
