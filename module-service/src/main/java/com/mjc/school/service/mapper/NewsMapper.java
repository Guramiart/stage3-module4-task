package com.mjc.school.service.mapper;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class, TagMapper.class, CommentMapper.class})
public abstract class NewsMapper {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected CommentRepository commentRepository;

    public abstract List<NewsDtoResponse> modelListToDtoList(List<NewsModel> modelList);

    @Mapping(source = "author", target = "authorDto")
    @Mapping(source = "tags", target = "tagsDto")
    @Mapping(source = "comment", target = "commentDto")
    public abstract NewsDtoResponse modelToDto(NewsModel model);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "author", expression = "java(authorRepository.getReference(dto.authorId()))")
    @Mapping(target = "tags", expression =
            "java(dto.tagsIds().stream().map(tagId -> tagRepository.getReference(tagId).toList()))")
    @Mapping(target = "comment", expression = "java(commentRepository.getReference(dto.commentId()))")
    public abstract NewsModel dtoToModel(NewsDtoRequest dto);
}
