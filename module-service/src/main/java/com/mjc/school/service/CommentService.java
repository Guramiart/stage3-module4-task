package com.mjc.school.service;

import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService extends BaseService<CommentDtoRequest, CommentDtoResponse, Long>{

    List<CommentDtoResponse> readCommentsPage(Pageable pageable);
    CommentDtoResponse readByNewsId(Long id);

}
