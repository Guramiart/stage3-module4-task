package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDBRepository extends AbstractDBRepository<CommentModel, Long> implements CommentRepository {

    @Override
    void update(CommentModel prevState, CommentModel nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

    @Override
    public Page<CommentModel> readCommentsPage(Pageable pageable) {
        Query query = entityManager.createQuery("SELECT c FROM CommentModel c");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<CommentModel> comments = query.getResultList();
        Query queryCount = entityManager.createQuery("SELECT COUNT(c) FROM CommentModel c");
        long count = (long) queryCount.getSingleResult();
        return new PageImpl<>(comments, pageable, count);
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
