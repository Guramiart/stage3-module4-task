package com.mjc.school.repository;

import com.mjc.school.repository.model.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagRepository extends BaseRepository<TagModel, Long> {

    Page<TagModel> readTagsPage(Pageable pageable);
    List<TagModel> readByNewsId(Long id);

}
