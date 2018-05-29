package com.example.jakub.todo;

public enum TASK_REQUEST {
    EDIT(0),
    CREATE(1);

    private final int value;

    private TASK_REQUEST(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
