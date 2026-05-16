package com.taskflow.taskflow;
import com.taskflow.taskflow.exception.TaskNotFoundException;
import com.taskflow.taskflow.model.*;
import com.taskflow.taskflow.repository.TaskRepository;
import com.taskflow.taskflow.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Test task", "description",
                    Priority.HIGH, LocalDateTime.now().plusDays(1));
                task.setId(1L);
    }
    @Test
    void getAllTasks_returnsList(){
        when(taskRepository.findAll()).thenReturn(List.of(task));
        List<Task> result = taskService.getAllTasks();
        assertEquals(1, result.size());
    }
    @Test
    void getById_returnsTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task task1 = taskService.getById(1L);
        assertEquals("Test task", task1.getTitle());
    }
    @Test
    void getById_throwsWhenNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getById(999L));
    }
    @Test
    void createTask_savesTask(){
        when(taskRepository.save(task)).thenReturn(task);
        Task task1 = taskService.createTask(task);
        verify(taskRepository).save(task);
    }
    @Test
    void updateTask_updatesTask(){
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task updated = new Task("Updated task" , "Updated description",
                Priority.HIGH, LocalDateTime.now().plusDays(2));
        when(taskRepository.save(any())).thenReturn(task);
        taskService.updateTask(1L, updated);
        verify(taskRepository).save(task);
    }
    @Test
    void deleteTask_deletesTask () {
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);
    }
    @Test
    void getByStatus_returnsList() {
        when(taskRepository.findByStatus(Status.TODO)).thenReturn(List.of(task));
        List<Task> result = taskService.getByStatus(Status.TODO);
        assertEquals(1, result.size());
    }

    @Test
    void getByPriority_returnsList() {
        when(taskRepository.findByPriority(Priority.HIGH)).thenReturn(List.of(task));
        List<Task> result = taskService.getByPriority(Priority.HIGH);
        assertEquals(1, result.size());
    }

    @Test
    void getByTitleContaining_returnsList() {
        when(taskRepository.findByTitleContaining("Test")).thenReturn(List.of(task));
        List<Task> result = taskService.getByTitleContaining("Test");
        assertEquals(1, result.size());
    }
    @Test
    void getByStatusAndPriority_returnsList() {
        when(taskRepository.findByStatusAndPriority(Status.TODO, Priority.HIGH)).thenReturn(List.of(task));
        List<Task> result = taskService.getByStatusAndPriority(Status.TODO, Priority.HIGH);
        assertEquals(1, result.size());
    }

    @Test
    void getByDeadlineBefore_returnsList() {
        LocalDateTime date = LocalDateTime.now().plusDays(7);
        when(taskRepository.findByDeadlineBefore(date)).thenReturn(List.of(task));
        List<Task> result = taskService.getByDeadlineBefore(date);
        assertEquals(1, result.size());
    }

    @Test
    void getByDeadlineAfter_returnsList() {
        LocalDateTime date = LocalDateTime.now().minusDays(7);
        when(taskRepository.findByDeadlineAfter(date)).thenReturn(List.of(task));
        List<Task> result = taskService.getByDeadlineAfter(date);
        assertEquals(1, result.size());
    }

    @Test
    void getByDescriptionContaining_returnsList() {
        when(taskRepository.findByDescriptionContaining("description")).thenReturn(List.of(task));
        List<Task> result = taskService.getByDescriptionContaining("description");
        assertEquals(1, result.size());
    }

    @Test
    void countByStatus() {
        when(taskRepository.countByStatus(Status.TODO)).thenReturn(1L);
        long result = taskService.countByStatus(Status.TODO);

        assertEquals(1L,result);
    }

    @Test
    void deleteByStatus_deletesTasks() {
        taskService.deleteByStatus(Status.DONE);

        verify(taskRepository).deleteByStatus(Status.DONE);
    }

    @Test
    void getByStatusOrderByDeadlineAsc_returnsList() {
        when(taskRepository.findByStatusOrderByDeadlineAsc(Status.TODO)).thenReturn(List.of(task));
        List<Task> result = taskService.getByStatusOrderByDeadlineAsc(Status.TODO);
        assertEquals(1, result.size());
    }

}









