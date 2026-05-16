package com.taskflow.taskflow.model;

public interface Assignable {
    void assignTo(String userId);
    String getAssigne();
}
