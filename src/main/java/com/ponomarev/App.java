package com.ponomarev;

import com.ponomarev.model.User;
import com.ponomarev.repository.UserRepository;
import com.ponomarev.service.UserService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        final var userRepository = new UserRepository();
        final var userService = new UserService(userRepository);

        var user = User.builder()
                .name("Oleg")
                .build();
        //userService.save(user);

        //System.out.println(userService.findById(4L));
        userService.findAll().forEach(System.out::println);
        //System.out.println(userService.existsById(5L));
        //userService.deleteById(5L);

        Iterable<User> users = List.of(User.builder().name("user1").build(),
                User.builder().name("user2").build());

        userService.saveAll(users).forEach(System.out::println);
        //userService.deleteAll();
    }
}