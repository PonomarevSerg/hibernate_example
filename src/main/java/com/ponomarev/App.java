package com.ponomarev;

import com.ponomarev.model.User;
import com.ponomarev.repository.UserRepository;
import com.ponomarev.service.UserService;

public class App {
    public static void main(String[] args) {
        final var userRepository = new UserRepository();
        final var userService = new UserService(userRepository);

        var user = User.builder()
                .name("Oleg")
                .build();

        System.out.println(userService.findById(4L));
        //userService.findAll().forEach(System.out::println);
    }
}