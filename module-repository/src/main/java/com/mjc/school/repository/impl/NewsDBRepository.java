package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.repository.query.NewsQueryParams;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Join;

import java.util.ArrayList;
import java.util.List;

public class NewsDBRepository extends AbstractDBRepository<NewsModel, Long> implements NewsRepository {
    @Override
    void update(NewsModel prevState, NewsModel nextState) {
        prevState.setTitle(nextState.getTitle());
        prevState.setContent(nextState.getContent());
        prevState.setAuthor(nextState.getAuthor());
        prevState.setTags(nextState.getTags());
        prevState.setComment(nextState.getComment());
    }

    @Override
    public List<NewsModel> readBySearchParams(NewsQueryParams newsQueryParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> criteriaQuery = criteriaBuilder.createQuery(NewsModel.class);
        Root<NewsModel> root = criteriaQuery.from(NewsModel.class);

        List<Predicate> predicates = new ArrayList<>();

        if(newsQueryParams.tagIds() != null || newsQueryParams.tagNames() != null) {
            Join<TagModel, NewsModel> newsTagJoin = root.join("tags");
            if(newsQueryParams.tagIds() != null) {
                predicates.add(newsTagJoin.get("id").in(newsQueryParams.tagIds()));
            }
            if(newsQueryParams.tagNames() != null) {
                predicates.add(newsTagJoin.get("name").in(newsQueryParams.tagNames()));
            }
        }

        if(newsQueryParams.author() != null) {
            Join<AuthorModel, NewsModel> newsAuthorJoin = root.join("author");
            predicates.add(criteriaBuilder.equal(newsAuthorJoin.get("name"), newsQueryParams.author()));
        }

        if(newsQueryParams.title() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + newsQueryParams.title()));
        }

        if(newsQueryParams.content() != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + newsQueryParams.content()));
        }

        criteriaQuery.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
