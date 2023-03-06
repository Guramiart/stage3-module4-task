package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.CommentMapper;
import com.mjc.school.service.validator.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readAll() {
        return commentMapper.modelListToDtoList(commentRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(Long id) {
        return commentRepository.readById(id)
                .map(commentMapper::modelToDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), id))
                );
    }

    @Override
    @Transactional
    public CommentDtoResponse create(@Valid CommentDtoRequest createRequest) {
        CommentModel model = commentMapper.dtoToModel(createRequest);
        return commentMapper.modelToDto(commentRepository.create(model));
    }

    @Override
    @Transactional
    public CommentDtoResponse update(@Valid CommentDtoRequest updateRequest) {
        if(commentRepository.existById(updateRequest.id())) {
            CommentModel model = commentMapper.dtoToModel(updateRequest);
            return commentMapper.modelToDto(commentRepository.update(model));
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id())
            );
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(commentRepository.existById(id)) {
            return commentRepository.deleteById(id);
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), id)
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readByNewsId(Long id) {
        return commentRepository.readByNewsId(id)
                .map(commentMapper::modelToDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), id))
                );
    }
}
