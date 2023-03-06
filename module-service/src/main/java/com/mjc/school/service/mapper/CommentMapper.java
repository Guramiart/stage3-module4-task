package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    List<CommentDtoResponse> modelListToDtoList(List<CommentModel> modelList);
    CommentDtoResponse modelToDto(CommentModel model);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "news", ignore = true)
    CommentModel dtoToModel(CommentDtoRequest dto);

}
