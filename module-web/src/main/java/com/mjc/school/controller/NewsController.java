package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.http.ResponseEntity;

public interface NewsController extends BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    ResponseEntity<NewsDtoResponse> readBySearchParams(NewsQueryParams newsQueryParams);

}
