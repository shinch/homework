package com.gmail.shinch.search_blog.app.controller.blog

import com.gmail.shinch.search_blog.app.event.CustomEventPublisher
import com.gmail.shinch.search_blog.app.event.keyword.KeywordEvent
import com.gmail.shinch.search_blog.app.event.search.SearchEngine
import com.gmail.shinch.search_blog.app.event.search.SearchErrorEvent
import com.gmail.shinch.search_blog.domain.blog.api_client.ClientType
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException
import com.gmail.shinch.search_blog.domain.blog.service.OrderType
import com.gmail.shinch.search_blog.domain.blog.service.SearchBlogResultDto
import com.gmail.shinch.search_blog.domain.blog.service.SearchService
import com.gmail.shinch.search_blog.domain.blog.service.SearchDto
import org.springframework.http.HttpStatus
import spock.lang.Specification

class BlogDelegatorTest extends Specification {
    def blogDelegator
    def kakaoService
    def naverService
    def customEventPublisher

    def setup() {
        kakaoService = Mock(SearchService)
        naverService = Mock(SearchService)
        customEventPublisher = Mock(CustomEventPublisher)
        blogDelegator = new BlogDelegator(kakaoService, naverService, customEventPublisher)
    }

    def "KAKAO로 블로그 검색 성공"() {
        given:
        def searchDto = new SearchDto()
        searchDto.keyword = "keywrod"
        searchDto.orderType = OrderType.ACCURACY
        searchDto.page = 1
        searchDto.size = 10
        def searchBlogResultDto = new SearchBlogResultDto()
        searchBlogResultDto.blogs = List.of()
        searchBlogResultDto.totalCount = 100

        when:
        blogDelegator.setSearchEngine(SearchEngine.KAKAO)
        def result = blogDelegator.getSearchResult(searchDto)

        then:
        1 * kakaoService.searchBlogByKeyword(searchDto) >> searchBlogResultDto
        0 * naverService.searchBlogByKeyword(searchDto)
        1 * customEventPublisher.publish(_ as KeywordEvent)

        and:
        result.blogs.size() == 0
        result.totalCount == searchBlogResultDto.totalCount
    }

    def "NAVER로 블로그 검색 성공"() {
        given:
        def searchDto = new SearchDto()
        searchDto.keyword = "keywrod"
        searchDto.orderType = OrderType.ACCURACY
        searchDto.page = 1
        searchDto.size = 10
        def searchBlogResultDto = new SearchBlogResultDto()
        searchBlogResultDto.blogs = List.of()
        searchBlogResultDto.totalCount = 100

        when:
        blogDelegator.setSearchEngine(SearchEngine.NAVER)
        def result = blogDelegator.getSearchResult(searchDto)

        then:
        0 * kakaoService.searchBlogByKeyword(searchDto)
        1 * naverService.searchBlogByKeyword(searchDto) >> searchBlogResultDto
        1 * customEventPublisher.publish(_ as KeywordEvent)

        and:
        result.blogs.size() == 0
        result.totalCount == searchBlogResultDto.totalCount
    }

    def "블로그 검색 실패"() {
        given:
        def searchDto = new SearchDto()
        searchDto.keyword = "keywrod"
        searchDto.orderType = OrderType.ACCURACY
        searchDto.page = 1
        searchDto.size = 10

        when:
        blogDelegator.setSearchEngine(SearchEngine.KAKAO)
        blogDelegator.getSearchResult(searchDto)

        then:
        1 * kakaoService.searchBlogByKeyword(searchDto) >> { throw new ExternalApiException(ClientType.KAKAO, HttpStatus.INTERNAL_SERVER_ERROR, "에러") }
        0 * naverService.searchBlogByKeyword(searchDto)
        0 * customEventPublisher.publish(_ as KeywordEvent)
        1 * customEventPublisher.publish(_ as SearchErrorEvent)

        and:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.KAKAO
        ex.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
    }
}
