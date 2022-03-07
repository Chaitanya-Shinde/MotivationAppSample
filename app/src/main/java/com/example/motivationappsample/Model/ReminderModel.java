package com.example.motivationappsample.Model;

public class ReminderModel {
    private  int id,status;
    private  String taskTitle, taskDesc, taskTime, taskDate, task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc(){
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc){
        this.taskDesc = taskDesc;
    }

    public String getTaskTime(){
        return taskTime;
    }

    public void setTaskTime(String taskTime){
        this.taskTime = taskTime;
    }

    public String getTaskDate(){
        return taskDate;
    }

    public void setTaskDate(String taskDate){
        this.taskDate = taskDate;
    }
}
