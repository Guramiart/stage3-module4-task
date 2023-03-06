package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AuthorDBRepository extends AbstractDBRepository<AuthorModel, Long> implements AuthorRepository {
    @Override
    void update(AuthorModel prevState, AuthorModel nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

    @Override
    public Optional<AuthorModel> readByNewsId(Long id) {
        TypedQuery<AuthorModel> typedQuery = entityManager
                .createQuery("SELECT a FROM AuthorModel a INNER JOIN a.news n WHERE n.id=:newsId", AuthorModel.class)
                .setParameter("newsId", id);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
