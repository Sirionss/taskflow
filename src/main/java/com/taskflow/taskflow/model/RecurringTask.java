package com.taskflow.taskflow.model;

import java.time.LocalDateTime;

public class RecurringTask extends Task {
    private int intervalDays;

    public RecurringTask(String title, String description,
                         Priority priority, LocalDateTime deadline,
                         int intervalDays) {
        super(title, description, priority, deadline);
        this.intervalDays =  intervalDays;
    }

    @Override
    public void markDone() {
        super.markDone();
        System.out.println("Next occurrence in " + intervalDays +" days");
        }

    @Override
    public String toString(){

        return "RecurringTask{id=" +  getId() +
                ", title= '" + getTitle() + '\'' +
                ", status= " + getStatus() +
                ", priority= " +getPriority() +
                ", intervalDays= " + intervalDays +'}';
    }

    public int getIntervalDays() {return intervalDays;}
}
