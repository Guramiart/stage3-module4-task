package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractDBRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> entityClass;
    private final Class<K> idClass;

    abstract void update(T prevState, T nextState);

    protected AbstractDBRepository() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        idClass = (Class<K>) parameterizedType.getActualTypeArguments()[1];
    }

    @Override
    public List<T> readAll() {
        return entityManager.createQuery("SELECT e FROM "
                + entityClass.getSimpleName()
                + " e", entityClass).getResultList();
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return readById(entity.getId()).map(e -> {
            update(e, entity);
            T updated = entityManager.merge(e);
            entityManager.flush();
            return updated;
        }).orElse(null);
    }

    @Override
    public boolean deleteById(K id) {
        if(id != null) {
            T entityReference = getReference(id);
            entityManager.remove(entityReference);
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(K id) {
        EntityType<T> entityType = entityManager.getMetamodel().entity(entityClass);
        String typeId = entityType.getId(idClass).getName();
        Query query = entityManager
                .createQuery("SELECT COUNT(*) FROM " + entityClass.getSimpleName() + " WHERE " + typeId + " = ?1")
                .setParameter(1, id);
        Long count = (Long)query.getSingleResult();
        return count > 0;
    }

    @Override
    public T getReference(K id) {
        return entityManager.getReference(entityClass, id);
    }
}
