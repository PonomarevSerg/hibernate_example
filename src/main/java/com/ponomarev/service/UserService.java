package com.ponomarev.service;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;
import com.ponomarev.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    public Iterable<User> saveAll(Iterable<User> entities) {
        return userRepository.saveAll(entities);
    }
    public void deleteAll() {
        userRepository.deleteAll();
    }
    public User update (User entity) {
        return userRepository.update(entity);
    }

    public User updateTask (User entity, List<Task> taskList) {
        return userRepository.updateTasks(entity, taskList);
    }
}
