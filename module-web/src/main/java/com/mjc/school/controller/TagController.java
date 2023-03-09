package com.mjc.school.controller;

import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.http.ResponseEntity;

public interface TagController extends BaseController<TagDtoRequest, TagDtoResponse, Long> {

    TagDtoResponse readByNewsId(Long newsId);

}
