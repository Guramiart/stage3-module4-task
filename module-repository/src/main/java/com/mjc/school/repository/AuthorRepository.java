package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorRepository extends BaseRepository<AuthorModel, Long> {

    Page<AuthorModel> readAuthorsPage(Pageable pageable);
    Optional<AuthorModel> readByNewsId(Long id);

}
