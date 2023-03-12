package com.mjc.school.service.query;

import java.util.List;

public record NewsQueryParams(
        List<Integer> tagIds,
        List<String> tagNames,
        String author,
        String title,
        String content) {
}
