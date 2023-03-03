package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record NewsDtoResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime lastUpdatedDate,
        AuthorDtoResponse authorDto,
        Set<TagDtoResponse> tagsDto,
        CommentDtoResponse commentDto) {
}
