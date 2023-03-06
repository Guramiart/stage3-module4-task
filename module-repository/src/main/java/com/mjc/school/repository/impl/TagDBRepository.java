package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class TagDBRepository extends AbstractDBRepository<TagModel, Long> implements TagRepository {
    @Override
    void update(TagModel prevState, TagModel nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

    @Override
    public Optional<TagModel> readByNewsId(Long id) {
        TypedQuery<TagModel> typedQuery = entityManager
                .createQuery("SELECT t FROM TagModel t INNER JOIN t.news n WHERE n.id=:newsId", TagModel.class)
                .setParameter("newsId", id);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
