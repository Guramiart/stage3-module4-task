package com.mjc.school.service.mapper;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public abstract class CommentMapper {
    @Autowired
    protected NewsRepository newsRepository;

    public abstract List<CommentDtoResponse> modelListToDtoList(List<CommentModel> modelList);
    @Mapping(source = "news", target = "newsDto")
    public abstract CommentDtoResponse modelToDto(CommentModel model);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "news", expression = "java(newsRepository.getReference(dto.newsId()))")
    public abstract CommentModel dtoToModel(CommentDtoRequest dto);

}
