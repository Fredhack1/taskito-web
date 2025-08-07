package com.taskito_web.backend.repository;

import com.taskito_web.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByIdDesc();
}
