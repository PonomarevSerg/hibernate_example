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

public class TaskRepository implements CrudRepository<Task, Long> {

    @Override
    public Task save(Task entity) {
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
    public List<Task> findAll() {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Task> cr = cb.createQuery(Task.class);
            Root<Task> root = cr.from(Task.class);
            cr.select(root);

            Query query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var stringQuery = "from Task task where task.id = :id";
            final var task = session.createQuery(stringQuery, Task.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(task);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Task> cr = cb.createQuery(Task.class);
            Root<Task> root = cr.from(Task.class);
            cr.select(root).where(cb.equal(root.get("id"), id));
            TypedQuery<Task> typedQuery = session.createQuery(cr);
            List<Task> resultList = typedQuery.getResultList();
            return !resultList.isEmpty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var transaction = session.beginTransaction();
            try {
                Task deleteTask = session.get(Task.class, id);
                session.remove(deleteTask);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iterable<Task> saveAll(Iterable<Task> entities) {
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
                CriteriaDelete<Task> criteriaDelete = cb.createCriteriaDelete(Task.class);
                Root<Task> root = criteriaDelete.from(Task.class);
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
    public Task update(Task entity) {
        final var stringQuery = "from Task task where task.id = :id";
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            final var task = session.createQuery(stringQuery, Task.class)
                    .setParameter("id", entity.getId())
                    .getSingleResult();
            if (task != null) {
                final var transaction = session.beginTransaction();
                task.setName(entity.getName());
                task.setUpdateDateTime(entity.getCreateDateTime());
                task.setUser(entity.getUser());
                task.setDescription(entity.getDescription());
                session.merge(task);
                transaction.commit();
            }
        }
        return entity;
    }

    public List<Task> showTasksForUser(User entity) {
        final var stringQuery = "from Task task where user.id = :id";
        try (final var session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(stringQuery, Task.class)
                    .setParameter("id", entity.getId())
                    .getResultList();
        }
    }
}
