package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Join;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsDBRepository extends AbstractDBRepository<NewsModel, Long> implements NewsRepository {
    @Override
    void update(NewsModel prevState, NewsModel nextState) {
        prevState.setTitle(nextState.getTitle());
        prevState.setContent(nextState.getContent());
        prevState.setAuthor(nextState.getAuthor());
        prevState.setTags(nextState.getTags());
        prevState.setComments(nextState.getComments());
    }

    @Override
    public List<NewsModel> readBySearchParams(NewsSearchQueryParams newsSearchQueryParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> criteriaQuery = criteriaBuilder.createQuery(NewsModel.class);
        Root<NewsModel> root = criteriaQuery.from(NewsModel.class);

        List<Predicate> predicates = new ArrayList<>();

        if(newsSearchQueryParams.tagIds() != null || newsSearchQueryParams.tagNames() != null) {
            Join<TagModel, NewsModel> newsTagJoin = root.join("tags");
            if(newsSearchQueryParams.tagIds() != null) {
                predicates.add(newsTagJoin.get("id").in(newsSearchQueryParams.tagIds()));
            }
            if(newsSearchQueryParams.tagNames() != null) {
                predicates.add(newsTagJoin.get("name").in(newsSearchQueryParams.tagNames()));
            }
        }

        if(newsSearchQueryParams.author() != null) {
            Join<AuthorModel, NewsModel> newsAuthorJoin = root.join("author");
            predicates.add(criteriaBuilder.equal(newsAuthorJoin.get("name"), newsSearchQueryParams.author()));
        }

        if(newsSearchQueryParams.title() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + newsSearchQueryParams.title()));
        }

        if(newsSearchQueryParams.content() != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + newsSearchQueryParams.content()));
        }

        criteriaQuery.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
