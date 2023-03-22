package com.gmail.shinch.search_blog.app.controller.keyword;

import com.gmail.shinch.search_blog.domain.keyword.service.KeywordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KeywordAppMapper {
    KeywordAppMapper INSTANCE = Mappers.getMapper(KeywordAppMapper.class);
    @Mappings({
            @Mapping(target = "seq", source = "seq"),
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "cnt", source = "cnt")
    })
    KeywordV1Response toKeywordV1Response(KeywordDto inDto);
    List<KeywordV1Response> toKeywordV1Responses(List<KeywordDto> inDtos);
}
