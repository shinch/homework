package com.gmail.shinch.search_blog.domain.keyword.service

import com.gmail.shinch.search_blog.domain.keyword.repository.KeywordEntity
import com.gmail.shinch.search_blog.domain.keyword.repository.KeywordRepository
import spock.lang.Specification

class KeywordServiceTest extends Specification {
    def keywordService
    def keywordRepository
    def keywordMapper

    def setup() {
        keywordRepository = Mock(KeywordRepository)
        keywordMapper = Mock(KeywordMapper)
        keywordService = new KeywordService(keywordRepository, keywordMapper)
    }

    def "상위 10개 키워드 검색" () {
        given:
        def keywordEntities = List.of(new KeywordEntity("keyword"))
        def keywordDtos = List.of(new KeywordDto())

        when:
        def result = keywordService.getTopTeenKeyword()

        then:
        1 * keywordRepository.findTop10ByOrderByCntDescKeywordAsc() >> keywordEntities
        1 * keywordMapper.toKeywordDtos(keywordEntities) >> keywordDtos
        result.size() == keywordDtos.size()
    }

    def "기존에 있는 검색어 검색시 검색 횟수 증가" () {
        given:
        def keyword = "keyword"

        when:
        keywordService.increaseCount(keyword)

        then:
        1 * keywordRepository.updateCount(keyword) >> 1
        0 * keywordRepository.save(_ as KeywordEntity)
    }

    def "신규 검색어 검색시 검색 횟수 증가" () {
        given:
        def keyword = "keyword"

        when:
        keywordService.increaseCount(keyword)

        then:
        1 * keywordRepository.updateCount(keyword) >> 0
        1 * keywordRepository.save(_ as KeywordEntity)
    }
}
