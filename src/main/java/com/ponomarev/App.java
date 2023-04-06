package com.ponomarev;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;
import com.ponomarev.repository.TaskRepository;
import com.ponomarev.repository.UserRepository;
import com.ponomarev.service.TaskService;
import com.ponomarev.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        final var userRepository = new UserRepository();
        final var userService = new UserService(userRepository);
        final var taskRepository = new TaskRepository();
        final var taskService = new TaskService(taskRepository);

//        userService.save(User.builder().name("name1").build());
//        userService.save(User.builder().name("name2").build());

/*        User user = User.builder()
                .name("name2")
                .build()
                .withTask(Task.builder()
                        .name("task1")
                        .deadline(LocalDateTime.now())
                        .description("descr")
                        .build());

        userService.save(user);*/

        taskService.save(Task.builder()
                .name("test")
                .description("desc")
                .deadline(LocalDateTime.now())
                .build());


//        userService.findAll().forEach(System.out::println);
//        userService.findAllUsersWithTasks().forEach(System.out::println);
//        taskRepository.findAll().forEach(System.out::println);
    }
}