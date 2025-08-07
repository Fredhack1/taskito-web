package com.taskito_web.backend.service;

import com.taskito_web.backend.model.Task;
import com.taskito_web.backend.model.TaskStatus;
import com.taskito_web.backend.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskServiceImpl;

    public TaskServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    // GET ALL
    @Test
    void shouldGetAllTasks() {
        // Arrange
        Task task1 = new Task("Tâche 1", "Description 1", TaskStatus.TO_DO, LocalDate.now());
        Task task2 = new Task("Tâche 2", "Description 2", TaskStatus.IN_PROGRESS, LocalDate.now());

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> result = taskServiceImpl.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    // GET BY ID
    @Test
    void shouldGetTaskById_WhenFound() {
        // Arrange
        Task task = new Task("Lecture", "Lire un livre.", TaskStatus.TO_DO, LocalDate.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Task result = taskServiceImpl.getTaskById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Lecture", result.getTitle());
    }

    // CREATE TASK
    @Test
    void shouldCreateTask() {
        // Arrange
        Task task = new Task("Apprendre", "Java Spring MVC", TaskStatus.IN_PROGRESS, LocalDate.now());
        when(taskRepository.save(task)).thenReturn(task);

        // Act
        Task result = taskServiceImpl.createTask(task);

        // Assert
        assertNotNull(result);
        verify(taskRepository, times(1)).save(task);
    }

    // UPDATE TASK
    @Test
    void shouldUpdateTask() {
        // Arrange
        // La tâche retournée par le repo
        Task existingTask = new Task("Ancien titre", "Description", TaskStatus.TO_DO, LocalDate.now());
        // La tâche à modifier (envoyé par l'user)
        Task updatedTask = new Task("Lecture", "Description", TaskStatus.IN_PROGRESS, LocalDate.now());
        // On simule la recherche
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        // On simule l'enregistrement
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        // Act
        Task result = taskServiceImpl.updateTask(1L, updatedTask);

        // Assert
        assertEquals("Lecture", result.getTitle());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(existingTask);
    }

    // DELETE TASK
    @Test
    void shouldDeleteTask() {
        // Arrange
        Long taskId = 1L;

        // Act
        taskServiceImpl.deleteTask(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    // THROW WHEN TASK ID NOT FOUND
    @Test
    void shouldThrowWhenTaskNotFound() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskServiceImpl.getTaskById(99L);
        });

        // Assert
        assertEquals("Task not found", exception.getMessage());
    }

}