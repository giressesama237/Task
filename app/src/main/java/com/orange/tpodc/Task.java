package com.orange.tpodc;

public class Task {
    private String taskTitle;
    private Boolean taskchecked;
    private int itemPosition;

    public String getTaskTitle() {
        return taskTitle;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public void setTaskchecked(boolean taskchecked) {
        this.taskchecked = taskchecked;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Boolean getTaskchecked() {
        return taskchecked;
    }

    public Task(){

     }
     public Task(String taskTitle, boolean taskchecked, int itemPosition){
        this.taskTitle = taskTitle;
        this.taskchecked = taskchecked;
        this.itemPosition = itemPosition;
     }

    @Override
    public String toString() {
        return "Task{" +
                "taskTitle='" + taskTitle + '\'' +
                ", taskchecked=" + taskchecked +
                ", itemPosition=" + itemPosition +
                '}';
    }
}
