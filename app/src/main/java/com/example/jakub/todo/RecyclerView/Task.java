package com.example.jakub.todo.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task {
    private long id;
    private String name,description;
    private Calendar date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task(String name, String description, Calendar date) {
        this.id = System.currentTimeMillis();
        this.name = name;
        this.description = description;
        this.date = date;

    }

    public Task() {
        this.id = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
