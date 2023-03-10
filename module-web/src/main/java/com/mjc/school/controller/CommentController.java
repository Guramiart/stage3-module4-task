package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;

public interface CommentController extends BaseController<CommentDtoRequest, CommentDtoResponse, Long> {

    CommentDtoResponse readByNewsId(Long newsId);

}
