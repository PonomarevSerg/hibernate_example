package com.ponomarev;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;
import com.ponomarev.repository.TaskRepository;
import com.ponomarev.repository.UserRepository;
import com.ponomarev.service.TaskService;
import com.ponomarev.service.UserService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        final var userRepository = new UserRepository();
        final var userService = new UserService(userRepository);
        final var taskRepository = new TaskRepository();
        final var taskService = new TaskService(taskRepository);

        var user = User.builder()
                .name("changedUser")
                .build();
        user.setId(1L);

//        userService.saveAll(
//                List.of(User.builder().name("user1").build(),
//                        User.builder().name("user2").build(),
//                        User.builder().name("user3").build())
//        );
//
//        taskService.saveAll(
//                List.of(Task.builder().name("task1").deadline(null).description("123").build(),
//                        Task.builder().name("task2").deadline(null).description("321").build())
//        );

        userService.findAll().forEach(System.out::println);
        taskService.findAll().forEach(System.out::println);

        List<Task> taskList = taskService.findAll();

        userService.updateTask(user, taskList);
        userService.findAll().forEach(System.out::println);



    }
}