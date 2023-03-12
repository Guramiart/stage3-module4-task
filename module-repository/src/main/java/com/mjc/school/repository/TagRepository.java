package com.mjc.school.repository;

import com.mjc.school.repository.model.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends BaseRepository<TagModel, Long> {

    List<TagModel> readByNewsId(Long id);

}
