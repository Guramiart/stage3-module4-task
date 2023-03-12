package com.mjc.school.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T, R, K> {

    List<R> readAll(int page, int size);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    R patch(K id, T updateRequest);

    ResponseEntity<Void> deleteById(K id);

}
