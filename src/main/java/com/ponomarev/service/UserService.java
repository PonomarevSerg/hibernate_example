package com.ponomarev.service;

import com.ponomarev.model.User;
import com.ponomarev.repository.UserRepository;
import lombok.RequiredArgsConstructor;

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
}
