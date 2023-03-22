package com.gmail.shinch.search_blog.domain.keyword.service

import com.gmail.shinch.search_blog.domain.keyword.repository.KeywordEntity
import spock.lang.Specification

class KeywordMapperTest extends Specification {
    def keywordMapper

    def setup() {
        keywordMapper = KeywordMapper.INSTANCE
    }

    def "KeywordEntity를 KeywordDto로 변경"() {
        given:
        def keywordEntity = new KeywordEntity("keyword")

        when:
        def result = keywordMapper.toKeywordDto(keywordEntity)

        then:
        result.keyword == keywordEntity.keyword
        result.cnt == keywordEntity.cnt
    }

    def "KeywordEntity목록을 KeywordDto목록으로 변경"() {
        given:
        def keywordEntity = new KeywordEntity("keyword")
        def keywordEntities = List.of(keywordEntity, keywordEntity, keywordEntity)

        when:
        def results = keywordMapper.toKeywordDtos(keywordEntities)

        then:
        results.size() == keywordEntities.size()
    }
}
