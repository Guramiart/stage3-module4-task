package com.mjc.school.repository;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.query.NewsQueryParams;

import java.util.List;

public interface NewsRepository extends BaseRepository<NewsModel, Long> {

    List<NewsModel> readBySearchParams(NewsQueryParams newsQueryParams);

}
