package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.query.NewsQueryParams;
import com.mjc.school.service.validator.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(
            NewsRepository newsRepository,
            AuthorRepository authorRepository,
            TagRepository tagRepository,
            CommentRepository commentRepository,
            NewsMapper newsMapper
    ) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readAll() {
        return newsMapper.modelListToDtoList(newsRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDtoResponse readById(Long id) {
        return newsRepository.readById(id)
                .map(newsMapper::modelToDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), id))
                );
    }

    @Override
    @Transactional
    public NewsDtoResponse create(@Valid NewsDtoRequest createRequest) {
        checkExistParams(createRequest);
        NewsModel model = newsMapper.dtoToModel(createRequest);
        return newsMapper.modelToDto(newsRepository.create(model));
    }

    @Override
    @Transactional
    public NewsDtoResponse update(@Valid NewsDtoRequest updateRequest) {
        if (!newsRepository.existById(updateRequest.id())) {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id())
            );
        }
        checkExistParams(updateRequest);
        NewsModel model = newsMapper.dtoToModel(updateRequest);
        return newsMapper.modelToDto(newsRepository.update(model));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if(newsRepository.existById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), id)
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readBySearchParams(NewsQueryParams newsQueryParams) {
        NewsSearchQueryParams newsSearchQueryParams = new NewsSearchQueryParams(
                newsQueryParams.tagNames(),
                newsQueryParams.tagIds(),
                newsQueryParams.author(),
                newsQueryParams.title(),
                newsQueryParams.content()
        );
        return newsMapper.modelListToDtoList(newsRepository.readBySearchParams(newsSearchQueryParams));
    }

    private Long findFirstNonExistentTagId(List<Long> tagsIds) {
        return tagsIds.stream()
                .filter(id -> !tagRepository.existById(id))
                .findFirst()
                .orElse(null);
    }

    private void checkExistParams(NewsDtoRequest request) {
        if(!authorRepository.existById(request.authorId())) {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), request.authorId())
            );
        }
        Long nonExistingTagId = findFirstNonExistentTagId(request.tagsIds());
        if(nonExistingTagId != null) {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), nonExistingTagId)
            );
        }
        if(!commentRepository.existById(request.commentId())) {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), request.commentId())
            );
        }
    }
}
