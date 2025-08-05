package com.taskito_web.backend.service;

import com.taskito_web.backend.model.Task;
import com.taskito_web.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task findById(Long id){
        return taskRepository.findById(id).orElse(null);
    }

    public Task saveOrUpdate(Task task){
        return taskRepository.save(task);
    }

    public void deleteById(Long id){
        taskRepository.deleteById(id);
    }
}
