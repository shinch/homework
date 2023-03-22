package com.gmail.shinch.search_blog.domain.blog.api_client.naver

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class NaverClientSendTest extends Specification {
    def restTemplate
    def apiHost
    def clientId
    def clientSecret
    def naverClient

    def setup() {
        apiHost = "openapi.naver.com"
        clientId = "2UcJH3zYVEVcyXo3GqnW"
        clientSecret = "sPTmYduh0f"
        restTemplate = new RestTemplate()
        naverClient = new NaverClient( apiHost, clientId, clientSecret, restTemplate )
    }

    def "GET /v1/search/blog.json 전송 확인"() {
        given:
        def naverSearchSend = new NaverSearchSend()
        naverSearchSend.setQuery("맛집")
        naverSearchSend.setSort("sim")
        naverSearchSend.setStart(1)
        naverSearchSend.setDisplay(5)

        when:
        def naverSearchReceive = naverClient.getSearchBlog(naverSearchSend)
        System.out.println(naverSearchReceive)

        then:
        naverSearchReceive != null
    }

}
