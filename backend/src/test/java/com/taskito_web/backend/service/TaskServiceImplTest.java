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

    @Test
    void shouldGetAllTasks() {
        Task task1 = new Task("Tâche 1", "Description 1", TaskStatus.TO_DO, LocalDate.now());
        Task task2 = new Task("Tâche 2", "Description 2", TaskStatus.IN_PROGRESS, LocalDate.now());

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskServiceImpl.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void shouldGetTaskById_WhenFound() {
        Task task = new Task("Lecture", "Lire un livre.", TaskStatus.TO_DO, LocalDate.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskServiceImpl.getTaskById(1L);

        assertEquals("Lecture", result.getTitle());
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task("Apprendre", "Java Spring MVC", TaskStatus.IN_PROGRESS, LocalDate.now());
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskServiceImpl.createTask(task);

        assertNotNull(result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldUpdateTask() {
        // La tâche retournée par le repo
        Task existingTask = new Task("Ancien titre", "Description", TaskStatus.TO_DO, LocalDate.now());

        // La tâche à modifier (envoyé par l'user)
        Task udatedTask = new Task("Lecture", "Description", TaskStatus.IN_PROGRESS, LocalDate.now());

        // On simule la recherche
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        // On simule l'enregistrement
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskServiceImpl.updateTask(1L, udatedTask);

        assertEquals("Lecture", result.getTitle());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void shouldDeleteTask() {
        Long taskId = 1L;

        // Act
        taskServiceImpl.deleteTask(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteById(taskId);
    }

}