package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDtoResponse(
        Long id,
        String name,
        LocalDateTime createdDate,
        LocalDateTime lastUpdatedDate,
        NewsDtoResponse newsDto) {
}
