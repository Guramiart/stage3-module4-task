package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService extends BaseService<NewsDtoRequest, NewsDtoResponse, Long>{

    List<NewsDtoResponse> readNewsPage(Pageable pageable);
    List<NewsDtoResponse> readBySearchParams(NewsQueryParams newsQueryParams);

}
