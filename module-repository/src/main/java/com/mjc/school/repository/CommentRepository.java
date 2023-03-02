package com.mjc.school.repository;

import com.mjc.school.repository.model.CommentModel;

import java.util.Optional;

public interface CommentRepository extends BaseRepository<CommentModel, Long> {

    Optional<CommentModel> readByNewsId(Long id);

}
