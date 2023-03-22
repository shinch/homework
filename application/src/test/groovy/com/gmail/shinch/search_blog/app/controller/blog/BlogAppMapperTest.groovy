package com.gmail.shinch.search_blog.app.controller.blog

import com.gmail.shinch.search_blog.domain.blog.service.BlogDto
import com.gmail.shinch.search_blog.domain.blog.service.OrderType
import spock.lang.Specification

class BlogAppMapperTest extends Specification {
    def blogAppMapper

    def setup() {
        blogAppMapper = BlogAppMapper.INSTANCE
    }

    def "요청 정보를 SearchDto로 변경"() {
        given:
        def keyword = "keyword"
        def orderType = "ACCURACY"
        def pageRangeInfo = PageRangeInfo.ofRequest("pages=1")

        when:
        def result = blogAppMapper.toSearchDto(keyword, orderType, pageRangeInfo)

        then:
        result.keyword == keyword
        result.orderType == OrderType.ACCURACY
        result.page == 1
        result.size == 10
    }

    def "blogDto를 BlogV1Response로 변경"() {
        given:
        def blogDto = new BlogDto()
        blogDto.name = "블로그 이름"
        blogDto.link = "http://ww.blog.com/test/10"
        blogDto.title = "게시물 제목"

        when:
        def result = blogAppMapper.toBlogV1Response(blogDto)

        then:
        result.name == blogDto.name
        result.link == blogDto.link
        result.title == blogDto.title
    }

    def "blogDto목록을 BlogV1Response목록으 변경"() {
        given:
        def blogDto = new BlogDto()
        blogDto.name = "블로그 이름"
        blogDto.link = "http://ww.blog.com/test/10"
        blogDto.title = "게시물 제목"
        def blogDtos = List.of(blogDto)

        when:
        def results = blogAppMapper.toBlogV1Responses(blogDtos)

        then:
        results.size() == 1
    }
}
