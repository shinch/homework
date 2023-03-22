package com.gmail.shinch.search_blog.domain.blog.service;

import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverClient;
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchReceive;
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchSend;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NaverService implements SearchService {
    private final NaverClient naverClient;
    private final BlogMapper blogMapper;
    @Override
    public SearchBlogResultDto searchBlogByKeyword(SearchDto searchDto) {
        NaverSearchSend naverSearchSend = blogMapper.toNaverSearchSend(searchDto);
        NaverSearchReceive naverSearchReceive = naverClient.getSearchBlog(naverSearchSend);
        SearchBlogResultDto searchBlogResultDto = blogMapper.toSearchBlogResultDtoByNaver(naverSearchReceive);
        return searchBlogResultDto;
    }
}
