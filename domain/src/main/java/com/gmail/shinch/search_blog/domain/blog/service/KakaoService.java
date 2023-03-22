package com.gmail.shinch.search_blog.domain.blog.service;

import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoClient;
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchSend;
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchReceive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KakaoService implements SearchService {
    private final KakaoClient kakaoClient;
    private final BlogMapper blogMapper;
    @Override
    public SearchBlogResultDto searchBlogByKeyword(SearchDto searchDto) {
        KakaoSearchSend kakaoSearchSend = blogMapper.toKakaoSearchSend(searchDto);
        KakaoSearchReceive kakaoSearchReceive = kakaoClient.getSearchBlog(kakaoSearchSend);
        SearchBlogResultDto searchBlogResultDto = blogMapper.toSearchBlogResultDtoByKakao(kakaoSearchReceive);
        return searchBlogResultDto;
    }
}
