package com.ponomarev.support;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;

import java.time.LocalDateTime;

public class DataProvider {
    public static User buildUser() {
        return User.builder()
                .name("name")
                .build()
                .withTask(buildTask());
    }

    public static Task buildTask() {
        return Task.builder()
                .name("name")
                .description("description")
                .deadline(LocalDateTime.now())
                .build();
    }
}
