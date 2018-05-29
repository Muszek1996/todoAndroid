package com.example.jakub.todo.Gson;

import com.example.jakub.todo.RecyclerView.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class TaskSerializer {
    static GsonBuilder gsonBuilder = new GsonBuilder();
    static Gson gson = gsonBuilder.create();
    public static String serialize(Task task){
        return gson.toJson(task);
    }
    public static Task deserialize(String task){
        return (Task)gson.fromJson(task,Task.class);
    }
}
