package com.taskflow.taskflow.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name= "tasks")
public class Task implements Assignable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime deadline;
    private String assigneeId;

    public Task(){}

    public Task(String title, String description, Priority priority, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = Status.TODO;
    }

    @Override
    public void assignTo(String userId){
        if (userId == null || userId.isBlank()){
            throw new IllegalArgumentException(
                    "userId cannot be empty");
        }
        this.assigneeId = userId;
    }
    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
    }
    public void markDone() {
        this.status = Status.DONE;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Status getStatus() {
        return status;
    }
    public Priority getPriority() {
        return priority;
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }
    @Override
    public String getAssigne() {
        return assigneeId;
    }

    @Override
    public String toString() {
        return "Task{id=" + id +
                ", title= '" + title + '\'' +
                ", status= " + status +
                ", priority= " +priority + '}';
    }

}
