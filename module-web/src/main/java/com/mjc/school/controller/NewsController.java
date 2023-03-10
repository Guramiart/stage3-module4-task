package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.List;

public interface NewsController extends BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    List<NewsDtoResponse> readBySearchParams(
            List<Integer> tagIds, List<String> tagNames, String author, String title, String content
    );

}
