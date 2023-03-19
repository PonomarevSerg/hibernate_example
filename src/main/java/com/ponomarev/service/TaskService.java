package com.ponomarev.service;

import com.ponomarev.model.Task;
import com.ponomarev.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    public Task findById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
    }
    public Task save(Task task) {
        return taskRepository.save(task);
    }
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
    public Iterable<Task> saveAll(Iterable<Task> entities) {
        return taskRepository.saveAll(entities);
    }
    public void deleteAll() {
        taskRepository.deleteAll();
    }
    public Task update (Task entity) {
        return taskRepository.update(entity);
    }
}
