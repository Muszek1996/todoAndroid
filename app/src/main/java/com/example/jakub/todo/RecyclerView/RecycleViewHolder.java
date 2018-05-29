package com.example.jakub.todo.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.jakub.todo.AddTaskActivity;
import com.example.jakub.todo.R;

import java.util.List;

public class RecycleViewHolder extends RecyclerView.ViewHolder {
    private static final String NAME = RecycleViewHolder.class.getSimpleName();

    private TextView taskName;
    private TextView taskDate;
    private FrameLayout frameLayout;
    private List<Task> tasks;
    protected Context context;

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public RecycleViewHolder(final View itemView, final List<Task> recycleViewHolderObject, final Context context) {
        super(itemView);
        this.tasks = recycleViewHolderObject;
        taskName = (TextView)itemView.findViewById(R.id.taskNameTextView);
        taskDate = (TextView)itemView.findViewById(R.id.taskDateTextView);
        frameLayout = (FrameLayout)itemView.findViewById(R.id.frameLayout);
        this.context = context;


    }

    public TextView getTaskName() {
        return taskName;
    }

    public TextView getTaskDate() {
        return taskDate;
    }


}
