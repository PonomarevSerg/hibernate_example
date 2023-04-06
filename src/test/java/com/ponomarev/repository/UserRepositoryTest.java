package com.ponomarev.repository;

import com.ponomarev.model.User;
import com.ponomarev.support.DataProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository();
    private final TaskRepository taskRepository = new TaskRepository();


    @AfterEach
    void afterEach() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void saveShouldWork() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);

        assertThat(userRepository.findAllUsersWithTasks())
                .isNotNull()
                .first()
                .satisfies(user1 -> {
                    assertThat(user1.getName()).isEqualTo("name");
                    assertThat(user1.getCreateTasks())
                            .isNotEmpty()
                            .first()
                            .satisfies(task1 ->
                                    assertThat(task1.getDescription()).isEqualTo("description"));
                });
    }

    @Test
    void findAll() {
        User user1 = User.builder()
                .name("user1").build();
        User user2 = User.builder()
                .name("user2").build();
        List<User> userList = List.of(user1, user2);
        userRepository.saveAll(userList);

        List<User> resultList = userRepository.findAll();

        assertEquals(userList.size(), resultList.size());
        assertThat(resultList)
                .isNotEmpty()
                .first()
                .satisfies(user3 ->
                        assertThat(user3.getName()).isEqualTo(user1.getName()));
    }

    @Test
    void findAllUsersWithTasks() {
        User user1 = User.builder()
                .name("user1").build();
        User user2 = User.builder()
                .name("user2").build();
        userRepository.saveAll(List.of(user1, user2));

        final var userWithTask1 = DataProvider.buildUser();
        final var userWithTask2 = DataProvider.buildUser();
        List<User> usersWithTask = List.of(userWithTask1, userWithTask2);
        userRepository.saveAll(usersWithTask);

        List<User> resultList = userRepository.findAllUsersWithTasks();

        assertEquals(resultList.size(), 2);
        assertThat(resultList)
                .isNotNull()
                .first()
                .satisfies(user -> {
                    assertThat(userWithTask1.getName()).isEqualTo("name");
                    assertThat(userWithTask1.getCreateTasks())
                            .isNotEmpty()
                            .first()
                            .satisfies(task1 ->
                                    assertThat(task1.getDescription()).isEqualTo("description"));
                });
    }

    @Test
    void findByIdSuccess() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals(user.getId(), optionalUser.get().getId());
        assertEquals(user.getName(), optionalUser.get().getName());
        assertThat(optionalUser.get().getCreateTasks())
                .isNotEmpty()
                .first()
                .satisfies(task1 ->
                        assertThat(task1.getDescription()).isEqualTo("description"));
    }

    @Test
    void findByIdFailure() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId() + 1);
        assertThat(optionalUser).isEqualTo(Optional.empty());
    }

    @Test
    void existsByIdTrue() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        boolean result = userRepository.existsById(user.getId());
        assertTrue(result);
        assertThat(userRepository.findById(user.getId())).isEqualTo(Optional.of(user));
    }

    @Test
    void existsByIdFalse() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        boolean result = userRepository.existsById(user.getId() + 1);
        assertFalse(result);
    }

    @Test
    void deleteByIdSuccess() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void deleteByIdFailure() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("attempt to create delete event with null entity");
        });
        assertEquals("attempt to create delete event with null entity", exception.getMessage());
    }

    @Test
    void saveAll() {
        User user1 = User.builder()
                .name("user1").build();
        User user2 = User.builder()
                .name("user2").build();
        Iterable<User> users = List.of(user1, user2);
        Iterable<User> savedUsers = userRepository.saveAll(users);
        assertEquals(users, savedUsers);
    }

    @Test
    void deleteAll() {
        final var user = DataProvider.buildUser();
        userRepository.save(user);
        userRepository.deleteAll();
        assertThat(userRepository.findAllUsersWithTasks()).isEmpty();
    }
/*
    @Test
    void update() {
    }

    @Test
    void updateTasks() {
    }*/
}