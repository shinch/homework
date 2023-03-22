package com.gmail.shinch.search_blog.domain.keyword.service;

import com.gmail.shinch.search_blog.domain.keyword.repository.KeywordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KeywordMapper {
    KeywordMapper INSTANCE = Mappers.getMapper(KeywordMapper.class);
    @Mappings({
            @Mapping(target = "seq", source = "seq"),
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "cnt", source = "cnt")
    })
    KeywordDto toKeywordDto(KeywordEntity inEntity);
    List<KeywordDto> toKeywordDtos(List<KeywordEntity> inEntities);
}
