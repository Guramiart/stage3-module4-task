package com.mjc.school.repository;

import com.mjc.school.repository.model.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentRepository extends BaseRepository<CommentModel, Long> {

    Page<CommentModel> readCommentsPage(Pageable pageable);
    Optional<CommentModel> readByNewsId(Long id);

}
