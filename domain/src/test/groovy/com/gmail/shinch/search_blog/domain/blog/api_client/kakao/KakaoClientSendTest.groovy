package com.gmail.shinch.search_blog.domain.blog.api_client.kakao

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class KakaoClientSendTest extends Specification {
    def kakaoClient
    def apiHost
    def apiKey
    def restTemplate

    def setup() {
        apiHost = "dapi.kakao.com"
        apiKey = "b905c3938904dd33b8cde6756a967a16"
        restTemplate = new RestTemplate()
        kakaoClient = new KakaoClient( apiHost, apiKey, restTemplate )
    }

    def "GET /v2/search/blog 전송 확인"() {
        given:
        def kakaoSearchSend = new KakaoSearchSend()
        kakaoSearchSend.setQuery("맛집")
        kakaoSearchSend.setSort("accuracy")
        kakaoSearchSend.setPage(1)
        kakaoSearchSend.setSize(5)

        when:
        def kakaoSearchReceive = kakaoClient.getSearchBlog(kakaoSearchSend)
        System.out.println(kakaoSearchReceive)

        then:
        kakaoSearchReceive != null
    }
}
