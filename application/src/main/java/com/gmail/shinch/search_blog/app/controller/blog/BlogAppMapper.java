package com.gmail.shinch.search_blog.app.controller.blog;

import com.gmail.shinch.search_blog.domain.blog.service.BlogDto;
import com.gmail.shinch.search_blog.domain.blog.service.OrderType;
import com.gmail.shinch.search_blog.domain.blog.service.SearchDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", imports = {OrderType.class})
public interface BlogAppMapper {
    BlogAppMapper INSTANCE = Mappers.getMapper(BlogAppMapper.class);

    @Mappings({
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "orderType", expression = "java( OrderType.valueOf(orderType) )"),
            @Mapping(target = "page", source = "pageRangeInfo.usePage"),
            @Mapping(target = "size", source = "pageRangeInfo.contentsPerPage")
    })
    SearchDto toSearchDto(String keyword, String orderType, PageRangeInfo pageRangeInfo);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "link", source = "link"),
            @Mapping(target = "title", source = "title")
    })
    BlogV1Response toBlogV1Response(BlogDto inDto);
    List<BlogV1Response> toBlogV1Responses(List<BlogDto> inDtos);

}
