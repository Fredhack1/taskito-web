package com.taskito_web.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskito_web.backend.model.Task;
import com.taskito_web.backend.model.TaskStatus;
import com.taskito_web.backend.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task sampleTask;

    @BeforeEach
    void setup() {
        // Arrange of all tests
        sampleTask = new Task("Lecture", "Lire un roman", TaskStatus.TO_DO, LocalDate.now());
        sampleTask.setId(1L);
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(sampleTask));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Lecture"));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(sampleTask);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Lire un roman"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        when(taskService.createTask(Mockito.any(Task.class))).thenReturn(sampleTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Lecture"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        when(taskService.updateTask(Mockito.eq(1L), Mockito.any(Task.class)))
                .thenReturn(sampleTask);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("TO_DO"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
