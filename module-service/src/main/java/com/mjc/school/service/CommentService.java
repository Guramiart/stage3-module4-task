package com.mjc.school.service;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;

public interface CommentService extends BaseService<CommentDtoRequest, CommentDtoResponse, Long>{

    CommentDtoResponse readByNewsId(Long id);

}
