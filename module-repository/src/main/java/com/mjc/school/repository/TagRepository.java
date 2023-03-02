package com.mjc.school.repository;

import com.mjc.school.repository.model.TagModel;

import java.util.Optional;

public interface TagRepository extends BaseRepository<TagModel, Long> {

    Optional<TagModel> readByNewsId(Long id);

}
