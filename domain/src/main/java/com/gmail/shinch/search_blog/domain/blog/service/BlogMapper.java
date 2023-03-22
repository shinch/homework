package com.gmail.shinch.search_blog.domain.blog.service;

import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.BlogDocument;
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchReceive;
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchSend;
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverBlogItem;
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchReceive;
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchSend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);
    @Mappings({
            @Mapping(target = "query", source = "keyword"),
            @Mapping(target = "sort", expression = "java( inDto.getOrderType().getKakaoType() )"),
            @Mapping(target = "page", source = "page"),
            @Mapping(target = "size", source = "size")
    })
    KakaoSearchSend toKakaoSearchSend(SearchDto inDto);
    @Mappings({
            @Mapping(target = "query", source = "keyword"),
            @Mapping(target = "sort", expression = "java( inDto.getOrderType().getNaverType() )"),
            @Mapping(target = "start", expression = "java( inDto.getPage() * inDto.getSize() - inDto.getSize() + 1 )"),
            @Mapping(target = "display", source = "size")
    })
    NaverSearchSend toNaverSearchSend(SearchDto inDto);

    @Mappings({
            @Mapping(target = "name", source = "blogname"),
            @Mapping(target = "link", source = "url"),
            @Mapping(target = "title", expression = "java( inVo.getTitle().replaceAll(\"<[^>]*>\", \"\") )")
    })
    BlogDto toBlogDtoByKakao(BlogDocument inVo);
    List<BlogDto> toBlogDtosByKakao(List<BlogDocument> inVos);

    @Mappings({
            @Mapping(target = "name", source = "bloggername"),
            @Mapping(target = "link", source = "link"),
            @Mapping(target = "title", expression = "java( inVo.getTitle().replaceAll(\"<[^>]*>\", \"\") )"),
    })
    BlogDto toBlogDtoByNaver(NaverBlogItem inVo);
    List<BlogDto> toBlogDtosByNaver(List<NaverBlogItem> inVos);

    @Mappings({
            @Mapping(target = "blogs", expression = "java( toBlogDtosByKakao(inVo.getDocuments()) )"),
            @Mapping(target = "totalCount", expression = "java( inVo.getMeta().getTotalCount() )"),
    })
    SearchBlogResultDto toSearchBlogResultDtoByKakao(KakaoSearchReceive inVo);
    @Mappings({
            @Mapping(target = "blogs", expression = "java( toBlogDtosByNaver(inVo.getItems()) )"),
            @Mapping(target = "totalCount", source = "total"),
    })
    SearchBlogResultDto toSearchBlogResultDtoByNaver(NaverSearchReceive inVo);
}
