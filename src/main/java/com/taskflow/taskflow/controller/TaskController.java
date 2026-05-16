package com.taskflow.taskflow.controller;

import com.taskflow.taskflow.model.*;
import com.taskflow.taskflow.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.getById(id);
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Task updateTask(@RequestBody Task taskUpdated, @PathVariable Long id){
        return taskService.updateTask(id, taskUpdated);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/status/{status}")
    public List<Task> getByStatus(@PathVariable Status status) {
        return taskService.getByStatus(status);
    }
    @GetMapping("/priority/{priority}")
    public List<Task> getByPriority(@PathVariable Priority priority) {
        return taskService.getByPriority(priority);
    }
    @GetMapping("title/{keyword}")
    public List<Task> getByTitleContaining(@PathVariable String keyword) {
        return taskService.getByTitleContaining(keyword);
    }
    @GetMapping("/status/{status}/priority/{priority}")
    public List<Task> getByStatusAndPriority(@PathVariable Status status,@PathVariable Priority priority){
        return taskService.getByStatusAndPriority(status, priority);
    }
    @GetMapping("/before/{date}")
    public List<Task> getByDeadlineBefore(@PathVariable LocalDateTime date){
        return taskService.getByDeadlineBefore(date);
    }
    @GetMapping("/after/{date}")
    public List<Task> getByDeadlineAfter(@PathVariable LocalDateTime date){
        return taskService.getByDeadlineAfter(date);
    }
    @GetMapping("/description/{keyword}")
    public List<Task> getByDescriptionContaining(@PathVariable String keyword){
        return taskService.getByDescriptionContaining(keyword);
    }
    @GetMapping("/status/{status}/count")
    public long countByStatus(@PathVariable Status status){
        return taskService.countByStatus(status);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/status/{status}")
    public void deleteByStatus(@PathVariable Status status){
        taskService.deleteByStatus(status);
    }
    @GetMapping("/status/{status}/sortedAsc")
    public List<Task> getByStatusOrderByDeadlineAsc(@PathVariable Status status){
        return taskService.getByStatusOrderByDeadlineAsc(status);
    }

}
