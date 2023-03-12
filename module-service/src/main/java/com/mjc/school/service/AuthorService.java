package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService extends BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {

    List<AuthorDtoResponse> readAuthorsPage(Pageable pageable);
    AuthorDtoResponse readByNewsId(Long id);

}
