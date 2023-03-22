package com.gmail.shinch.search_blog.app.controller.blog;

import com.gmail.shinch.search_blog.app.event.CustomEventPublisher;
import com.gmail.shinch.search_blog.app.event.keyword.KeywordEvent;
import com.gmail.shinch.search_blog.app.event.search.SearchEngine;
import com.gmail.shinch.search_blog.app.event.search.SearchErrorEvent;
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException;
import com.gmail.shinch.search_blog.domain.blog.service.SearchBlogResultDto;
import com.gmail.shinch.search_blog.domain.blog.service.SearchDto;
import com.gmail.shinch.search_blog.domain.blog.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogDelegator {
    @Setter
    private SearchEngine searchEngine = SearchEngine.KAKAO;
    private final SearchService kakaoService;
    private final SearchService naverService;
    private final CustomEventPublisher customEventPublisher;

    public SearchBlogResultDto getSearchResult(SearchDto searchDto) {
        try {
            SearchBlogResultDto searchBlogResultDto = switch (searchEngine) {
                case KAKAO -> kakaoService.searchBlogByKeyword(searchDto);
                case NAVER -> naverService.searchBlogByKeyword(searchDto);
            };
            KeywordEvent keywordEvent = new KeywordEvent(this, searchDto.getKeyword());
            customEventPublisher.publish(keywordEvent);
            return searchBlogResultDto;
        } catch (ExternalApiException ex) {
            SearchErrorEvent searchErrorEvent = new SearchErrorEvent(this, SearchEngine.valueOf(ex.getClientType().name()), ex.getHttpStatus());
            customEventPublisher.publish(searchErrorEvent);
            throw ex;
        }
    }
}