package com.gmail.shinch.search_blog.domain.blog.service

import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoClient
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchReceive
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchSend
import spock.lang.Specification

class KakaoServiceTest extends Specification {
    def kakaoService
    def kakaoClient
    def blogMapper

    def setup() {
        kakaoClient = Mock(KakaoClient)
        blogMapper = Mock(BlogMapper)
        kakaoService = new KakaoService(kakaoClient, blogMapper)
    }

    def "키워드를 포함한 블로그 검색"() {
        given:
        def searchDto = new SearchDto()
        def kakaoSearchSend = new KakaoSearchSend()
        def kakaoSearchReceive = new KakaoSearchReceive()
        def searchBlogResultDto = new SearchBlogResultDto()
        searchBlogResultDto.totalCount = 10

        when:
        def result = kakaoService.searchBlogByKeyword(searchDto)

        then:
        1 * blogMapper.toKakaoSearchSend(searchDto) >> kakaoSearchSend
        1 * kakaoClient.getSearchBlog(kakaoSearchSend) >> kakaoSearchReceive
        1 * blogMapper.toSearchBlogResultDtoByKakao(kakaoSearchReceive) >> searchBlogResultDto
        result.totalCount == searchBlogResultDto.totalCount

    }
}
