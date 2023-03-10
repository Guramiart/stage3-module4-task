package com.mjc.school.service.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.validator.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readAll() {
        return tagMapper.modelListToDtoList(tagRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse readById(Long id) {
        return tagRepository.readById(id)
                .map(tagMapper::modelToDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), id))
                );
    }

    @Override
    @Transactional
    public TagDtoResponse create(@Valid TagDtoRequest createRequest) {
        TagModel model = tagMapper.dtoToModel(createRequest);
        return tagMapper.modelToDto(tagRepository.create(model));
    }

    @Override
    @Transactional
    public TagDtoResponse update(@Valid TagDtoRequest updateRequest) {
        if(tagRepository.existById(updateRequest.id())) {
            TagModel model = tagMapper.dtoToModel(updateRequest);
            return tagMapper.modelToDto(tagRepository.update(model));
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id())
            );
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(tagRepository.existById(id)) {
            return tagRepository.deleteById(id);
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), id)
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readByNewsId(Long id) {
        return tagMapper.modelListToDtoList(tagRepository.readByNewsId(id));
    }
}
