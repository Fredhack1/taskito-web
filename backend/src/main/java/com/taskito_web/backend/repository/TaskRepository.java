package com.taskito_web.backend.repository;

import com.taskito_web.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Long id(Long id);
}
