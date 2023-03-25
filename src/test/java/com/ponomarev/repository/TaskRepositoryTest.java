package com.ponomarev.repository;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {
    UserRepository userRepository = new UserRepository();
    TaskRepository taskRepository = new TaskRepository();

    @Test
    void showTasksForUser() {
        var user = User.builder().name("UserTest").build();
        userRepository.save(user);

        var task = Task.builder()
                .name("taskTest")
                .description("user2_task")
                .deadline(LocalDateTime.now())
                .user(user)
                .build();
        taskRepository.save(task);

        // Получаем список задач для созданного пользователя
        List<Task> tasks = taskRepository.showTasksForUser(user);

        assertTrue(tasks.contains(task));
    }
}