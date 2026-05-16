package com.taskflow.taskflow.service;
import com.taskflow.taskflow.model.*;
import com.taskflow.taskflow.repository.TaskRepository;
import com.taskflow.taskflow.exception.TaskNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getById(Long id) {
        return  taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updated){
        Task task = getById(id);
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setPriority(updated.getPriority());
        task.setStatus(updated.getStatus());
        task.setDeadline(updated.getDeadline());
        task.setAssigneeId(updated.getAssigne());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }
    public List<Task> getByPriority(Priority priority){
        return taskRepository.findByPriority(priority);
    }


    public List<Task> getByTitleContaining(String keyword) {
        return taskRepository.findByTitleContaining(keyword);
    }
    public List<Task> getByStatusAndPriority(Status status, Priority priority){
        return taskRepository.findByStatusAndPriority(status, priority);
    }
    public List<Task> getByDeadlineBefore(LocalDateTime date){
        return taskRepository.findByDeadlineBefore(date);
    }
    public List<Task> getByDeadlineAfter(LocalDateTime date){
        return taskRepository.findByDeadlineAfter(date);
    }
    public List<Task> getByDescriptionContaining(String keyword){
        return taskRepository.findByDescriptionContaining(keyword);
    }
    public long countByStatus(Status status){
        return taskRepository.countByStatus(status);
    }
    @Transactional
    public void deleteByStatus(Status status){
        taskRepository.deleteByStatus(status);
    }
    public List<Task> getByStatusOrderByDeadlineAsc(Status status){
        return taskRepository.findByStatusOrderByDeadlineAsc(status);
    }
}

