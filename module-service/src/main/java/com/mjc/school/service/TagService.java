package com.mjc.school.service;

import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

public interface TagService extends BaseService<TagDtoRequest, TagDtoResponse, Long>{

    TagDtoResponse readByNewsId(Long id);

}
