package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.http.ResponseEntity;

public interface CommentController extends BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    CommentDtoResponse readByNewsId(Long newsId);

}
