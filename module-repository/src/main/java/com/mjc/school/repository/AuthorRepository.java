package com.mjc.school.repository;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;

import java.util.Optional;

public interface AuthorRepository extends BaseRepository<AuthorModel, Long> {

    Optional<AuthorModel> readByNewsId(Long id);

}
