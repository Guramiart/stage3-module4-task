package com.mjc.school.service.query;

import java.util.List;

public record NewsQueryParams(
        List<Long> tagIds,
        List<Long> tagNames,
        String author,
        String title,
        String content) {
}
