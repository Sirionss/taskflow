package com.taskflow.taskflow.repository;

import com.taskflow.taskflow.model.Task;
import com.taskflow.taskflow.model.Status;
import com.taskflow.taskflow.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByTitleContaining(String keyword);
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatusAndPriority(Status status, Priority priority);
    List<Task> findByDeadlineBefore(LocalDateTime date);
    List<Task> findByDeadlineAfter(LocalDateTime date);
    List<Task> findByDescriptionContaining(String keyword);
    long countByStatus(Status status);
    void deleteByStatus(Status status);
    List<Task> findByStatusOrderByDeadlineAsc(Status status);
}
