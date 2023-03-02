package com.mjc.school.repository.query;

import java.util.List;

public record NewsQueryParams(
   List<String> tagNames,
   List<Integer> tagIds,
   String author,
   String title,
   String content
) {}
