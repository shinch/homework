package com.gmail.shinch.search_blog.domain.blog.service

import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverClient
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchReceive
import com.gmail.shinch.search_blog.domain.blog.api_client.naver.NaverSearchSend
import spock.lang.Specification

class NaverServiceTest extends Specification {
    def naverService
    def naverClient
    def blogMapper

    def setup() {
        naverClient = Mock(NaverClient)
        blogMapper = Mock(BlogMapper)
        naverService = new NaverService(naverClient, blogMapper)
    }

    def "키워드를 포함한 블로그 검색"() {
        given:
        def searchDto = new SearchDto()
        def naverSearchSend = new NaverSearchSend()
        def naverSearchReceive = new NaverSearchReceive()
        def searchBlogResultDto = new SearchBlogResultDto()
        searchBlogResultDto.totalCount = 10

        when:
        def result = naverService.searchBlogByKeyword(searchDto)

        then:
        1 * blogMapper.toNaverSearchSend(searchDto) >> naverSearchSend
        1 * naverClient.getSearchBlog(naverSearchSend) >> naverSearchReceive
        1 * blogMapper.toSearchBlogResultDtoByNaver(naverSearchReceive) >> searchBlogResultDto
        result.totalCount == searchBlogResultDto.totalCount

    }
}
