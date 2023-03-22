package com.gmail.shinch.search_blog.app.event.keyword

import com.gmail.shinch.search_blog.domain.keyword.service.KeywordService
import spock.lang.Specification

class KeywordEventListenerTest extends Specification {
    def keywordService
    def keywordEventListener

    def setup() {
        keywordService = Mock(KeywordService)
        keywordEventListener = new KeywordEventListener(keywordService)
    }

    def "키워드 검색 이벤트 처리"() {
        given:
        def event = new KeywordEvent(this, "keyword")
        def keyword = "keyword"

        when:
        keywordEventListener.searchKeyword(event)

        then:
        1 * keywordService.increaseCount(keyword)
    }
}
