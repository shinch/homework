package com.gmail.shinch.search_blog.app.event.search

import com.gmail.shinch.search_blog.app.controller.blog.BlogDelegator
import org.springframework.http.HttpStatus
import spock.lang.Specification

class SearchErrorEventListenerTest extends Specification {
    def blogDelegator
    def searchErrorEventListener

    def setup() {
        blogDelegator = Mock(BlogDelegator)
        searchErrorEventListener = new SearchErrorEventListener(blogDelegator)
    }

    def "KAKAO로 검색 엔진 변경"() {
        given:
        def event = new SearchErrorEvent(this, SearchEngine.NAVER, HttpStatus.BAD_REQUEST)

        when:
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)

        then:
        1 * blogDelegator.setSearchEngine(SearchEngine.KAKAO)
        0 * blogDelegator.setSearchEngine(SearchEngine.NAVER)
    }

    def "NAVER로 검색 엔진 변경"() {
        given:
        def event = new SearchErrorEvent(this, SearchEngine.KAKAO, HttpStatus.BAD_REQUEST)

        when:
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)
        searchErrorEventListener.searchError(event)

        then:
        0 * blogDelegator.setSearchEngine(SearchEngine.KAKAO)
        1 * blogDelegator.setSearchEngine(SearchEngine.NAVER)
    }
}
