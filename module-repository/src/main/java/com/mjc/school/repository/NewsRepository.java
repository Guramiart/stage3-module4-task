package com.mjc.school.repository;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsRepository extends BaseRepository<NewsModel, Long> {

    Page<NewsModel> readNewsPage(Pageable pageable);
    List<NewsModel> readBySearchParams(NewsSearchQueryParams newsSearchQueryParams);

}
