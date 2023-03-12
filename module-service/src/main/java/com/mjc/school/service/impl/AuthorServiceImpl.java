package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDtoResponse> readAll() {
        return authorMapper.modelListToDtoList(authorRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readById(Long id) {
        return authorRepository.readById(id)
                .map(authorMapper::modelToDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id))
                );
    }

    @Override
    @Transactional
    public AuthorDtoResponse create(@Valid AuthorDtoRequest createRequest) {
        AuthorModel model = authorMapper.dtoToModel(createRequest);
        return authorMapper.modelToDto(authorRepository.create(model));
    }

    @Override
    @Transactional
    public AuthorDtoResponse update(@Valid AuthorDtoRequest updateRequest) {
        if(authorRepository.existById(updateRequest.id())){
            AuthorModel model = authorMapper.dtoToModel(updateRequest);
            return authorMapper.modelToDto(authorRepository.update(model));
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id())
            );
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(authorRepository.existById(id)) {
            return authorRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readByNewsId(Long id) {
        return authorRepository.readByNewsId(id)
                .map(authorMapper::modelToDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), id))
                );
    }

}
