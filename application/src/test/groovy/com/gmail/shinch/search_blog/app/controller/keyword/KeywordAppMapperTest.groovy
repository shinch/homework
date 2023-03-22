package com.gmail.shinch.search_blog.app.controller.keyword

import com.gmail.shinch.search_blog.domain.keyword.service.KeywordDto
import spock.lang.Specification

class KeywordAppMapperTest extends Specification {
    def keywordAppMapper

    def setup() {
        keywordAppMapper = KeywordAppMapper.INSTANCE
    }

    def "KeywordDto를 KeywordV1Response로 변경"() {
        given:
        def keywordDto = new KeywordDto()
        keywordDto.seq = 10
        keywordDto.keyword = "keyword"
        keywordDto.cnt = 100

        when:
        def result = keywordAppMapper.toKeywordV1Response(keywordDto)

        then:
        result.seq == keywordDto.seq
        result.keyword == keywordDto.keyword
        result.cnt == keywordDto.cnt
    }

    def "KeywordDto목록을 KeywordV1Response목록으로 변경"() {
        given:
        def keywordDto = new KeywordDto()
        keywordDto.seq = 10
        keywordDto.keyword = "keyword"
        keywordDto.cnt = 100
        def keywordDtos = List.of(keywordDto)

        when:
        def results = keywordAppMapper.toKeywordV1Responses(keywordDtos)

        then:
        results.size() == 1
    }
}
