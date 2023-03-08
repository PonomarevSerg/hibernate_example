package com.ponomarev.repository;

import com.ponomarev.model.User;
import com.ponomarev.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<User, Long> {

    @Override
    public User save(User entity) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return entity;
    }

    @Override
    public List<User> findAll() {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root);

            Query query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable((User) session.createQuery(
                    "from User user where user.id = :id").setParameter("id", id).getSingleResult());
        }
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Iterable<User> saveAll(Iterable<User> entities) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
