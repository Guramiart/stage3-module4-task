package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDBRepository extends AbstractDBRepository<TagModel, Long> implements TagRepository {
    @Override
    void update(TagModel prevState, TagModel nextState) {
        prevState.setName(nextState.getName());
        prevState.setNews(nextState.getNews());
    }

    @Override
    public boolean deleteById(Long id) {
        if(id != null) {
            TagModel entity = readById(id).get();
            for (NewsModel model : entity.getNews()) {
                model.removeTag(entity);
                entityManager.merge(model);
            }
            entityManager.remove(entityManager.merge(entity));
            return true;
        }
        return false;
    }

    @Override
    public List<TagModel> readByNewsId(Long id) {
        TypedQuery<TagModel> typedQuery = entityManager
                .createQuery("SELECT t FROM TagModel t INNER JOIN t.news n WHERE n.id=:newsId", TagModel.class)
                .setParameter("newsId", id);
        return typedQuery.getResultList();
    }
}
