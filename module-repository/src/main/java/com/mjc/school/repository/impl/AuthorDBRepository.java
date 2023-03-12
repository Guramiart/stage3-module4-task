package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.AuthorRepository;
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
public class AuthorDBRepository extends AbstractDBRepository<AuthorModel, Long> implements AuthorRepository {
    @Override
    void update(AuthorModel prevState, AuthorModel nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

    @Override
    public Page<AuthorModel> readAuthorsPage(Pageable pageable) {
        Query query = entityManager.createQuery("SELECT a FROM AuthorModel a");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<AuthorModel> authors = query.getResultList();
        Query queryCount = entityManager.createQuery("SELECT COUNT(a) FROM AuthorModel a");
        long count = (long) queryCount.getSingleResult();
        return new PageImpl<>(authors, pageable, count);
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
