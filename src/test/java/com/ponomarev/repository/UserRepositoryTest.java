package com.ponomarev.repository;

import com.ponomarev.model.User;
import com.ponomarev.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();

    @Test
    void save() {
        var session = mock(Session.class);
        var transaction = mock(Transaction.class);

        when(HibernateUtil.getSessionFactory().openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        var user = mock(User.class);

        var savedUser = userRepository.save(user);

        verify(session).persist(user);
        verify(transaction).commit();

        assertEquals(user, savedUser);
    }

    @Test
    void findAll() {
        userRepository.deleteAll();
        User user1 = User.builder()
                .name("user1").build();
        User user2 = User.builder()
                .name("user2").build();
        List<User> userList = List.of(user1, user2);
        userRepository.saveAll(userList);

        List<User> result = userRepository.findAll();

        assertEquals(result.size(), userList.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals("user2", result.get(1).getName());
    }

    @Test
    void findById() {
        var user = User.builder().name("UserTest").build();
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findById(user.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals(user.getId(), optionalUser.get().getId());
        assertEquals(user.getName(), optionalUser.get().getName());
    }

    @Test
    void existsById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void update() {
    }

    @Test
    void updateTasks() {
    }
}