package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class CommentDBRepository extends AbstractDBRepository<CommentModel, Long> implements CommentRepository {

    @Override
    void update(CommentModel prevState, CommentModel nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

    @Override
    public Optional<CommentModel> readByNewsId(Long id) {
        TypedQuery<CommentModel> typedQuery = entityManager
                .createQuery("SELECT c FROM CommentModel c INNER JOIN c.news n WHERE n.id=:newsId", CommentModel.class)
                .setParameter("newsId", id);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
