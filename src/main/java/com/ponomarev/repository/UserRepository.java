package com.ponomarev.repository;

import com.ponomarev.model.Task;
import com.ponomarev.model.User;
import com.ponomarev.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
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
            final var stringQuery = "from User user where user.id = :id";
            final var user = session.createQuery(stringQuery, User.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("id"), id));
            TypedQuery<User> typedQuery = session.createQuery(cr);
            List<User> resultList = typedQuery.getResultList();
            return !resultList.isEmpty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var transaction = session.beginTransaction();
            try {
                User deleteUser = session.get(User.class, id);
                session.remove(deleteUser);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> entities) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var transaction = session.beginTransaction();
            try {
                entities.forEach(session::persist);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        return entities;
    }

    @Override
    public void deleteAll() {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var transaction = session.beginTransaction();
            try {
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaDelete<User> criteriaDelete = cb.createCriteriaDelete(User.class);
                Root<User> root = criteriaDelete.from(User.class);
                criteriaDelete.where(cb.isNotNull(root));
                session.createQuery(criteriaDelete).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public User update(User entity) {
        final var stringQuery = "from User user where user.id = :id";
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var user = session.createQuery(stringQuery, User.class)
                    .setParameter("id", entity.getId())
                    .getSingleResult();
            if (user != null) {
                final var transaction = session.beginTransaction();
                user.setName(entity.getName());
                user.setUpdateDateTime(entity.getCreateDateTime());
                user.setCreateTasks(entity.getCreateTasks());
                session.merge(user);
                transaction.commit();
            }
        }
        return entity;
    }

    public User updateTasks(User entity, List<Task> taskList) {
        final var stringQuery = "from User user where user.id = :id";
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var user = session.createQuery(stringQuery, User.class)
                    .setParameter("id", entity.getId())
                    .getSingleResult();
            if (taskList != null) {
                taskList.forEach(t -> t.setUser(entity));
            }
            session.merge(user);
        }
        return entity;
    }
}
