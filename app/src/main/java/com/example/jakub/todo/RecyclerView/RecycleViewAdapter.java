package com.example.jakub.todo.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jakub.todo.AddTaskActivity;
import com.example.jakub.todo.Gson.TaskSerializer;
import com.example.jakub.todo.R;
import com.example.jakub.todo.mainTODO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
    private List<Task> tasks;
    protected Context context;

    public RecycleViewAdapter(List<Task> task, Context context) {
        this.tasks = task;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        RecycleViewHolder viewHolder = new RecycleViewHolder(layoutView, tasks,context);
        return viewHolder;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    private void editTask(Task task){
        ((mainTODO)context).editTask(task);
    }
    private void removeTask(String key){
        ((mainTODO)context).removeTask(key);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, final int index) {
        Task task = tasks.get(index);


        holder.getTaskName().setText(tasks.get(index).getName());
        Calendar taskDate = tasks.get(index).getDate();
        holder.getTaskDate().setText(new SimpleDateFormat("dd.MM.yyyy").format(taskDate.getTime()));
        if(taskDate.before(Calendar.getInstance())){
            holder.getTaskDate().setTextColor(Color.RED);
        }else{
            holder.getTaskDate().setTextColor(Color.BLACK);
        }


        holder.getFrameLayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.getTaskDate());
                popup.inflate(R.menu.menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuEditOpt:
                                editTask(task);
                                notifyDataSetChanged();
                                break;
                            case R.id.menuDeleteOpt:
                                removeTask(String.valueOf(tasks.get(index).getId()));
                                tasks.remove(index);
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }



}