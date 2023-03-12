package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    List<TagDtoResponse> modelListToDtoList(List<TagModel> models);

    TagDtoResponse modelToDto(TagModel model);

    @Mapping(target = "news", ignore = true)
    TagModel dtoToModel(TagDtoRequest dto);

}
