package com.ponomarev;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;
import com.ponomarev.repository.TaskRepository;
import com.ponomarev.repository.UserRepository;
import com.ponomarev.service.TaskService;
import com.ponomarev.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        final var userRepository = new UserRepository();
        final var userService = new UserService(userRepository);
        final var taskRepository = new TaskRepository();
        final var taskService = new TaskService(taskRepository);

        userService.findAll().forEach(System.out::println);
        taskService.findAll().forEach(System.out::println);
    }
}