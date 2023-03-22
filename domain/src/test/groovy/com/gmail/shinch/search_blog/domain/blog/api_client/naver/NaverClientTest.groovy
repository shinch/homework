package com.gmail.shinch.search_blog.domain.blog.api_client.naver

import com.gmail.shinch.search_blog.domain.blog.api_client.ClientType
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.BlogMeta
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchReceive
import com.gmail.shinch.search_blog.domain.blog.api_client.kakao.KakaoSearchSend
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class NaverClientTest extends Specification {
    def restTemplate
    def apiHost
    def clientId
    def clientSecret
    def naverClient

    def setup() {
        apiHost = "openapi.naver.com"
        clientId = "2UcJH3zYVEVcyXo3GqnW"
        clientSecret = "sPTmYduh0f"
        restTemplate = Mock(RestTemplate)
        naverClient = new NaverClient( apiHost, clientId, clientSecret, restTemplate )
    }

    def "블로그 조회 성공"() {
        given:
        def naverSearchSend = new NaverSearchSend()
        naverSearchSend.query = "맛집"
        naverSearchSend.sort = "sim"
        naverSearchSend.start = 10
        naverSearchSend.display = 100

        def naverSearchReceive = new NaverSearchReceive()
        naverSearchReceive.lastBuildDate = "lastBuildDate"
        naverSearchReceive.total = 100
        naverSearchReceive.start = 1
        naverSearchReceive.display = 10
        naverSearchReceive.items = List.of()

        def response = new ResponseEntity(naverSearchReceive, HttpStatusCode.valueOf(200))

        when:
        def checkData = naverClient.getSearchBlog(naverSearchSend)

        then:
        1 * restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> response
        checkData.items.size() == naverSearchReceive.items.size()
        checkData.lastBuildDate == naverSearchReceive.lastBuildDate
        checkData.total == naverSearchReceive.total
        checkData.start == naverSearchReceive.start
        checkData.display == naverSearchReceive.display
    }

    def "블로그 조회시 Client Error 발생"() {
        given:
        def naverSearchSend = new NaverSearchSend()
        naverSearchSend.query = "맛집"
        naverSearchSend.sort = "sim"
        naverSearchSend.start = 10
        naverSearchSend.display = 100

        and:
        restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> { throw new HttpClientErrorException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), "error message") }

        when:
        naverClient.getSearchBlog(naverSearchSend)

        then:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.NAVER
        ex.httpStatus == HttpStatus.BAD_REQUEST
    }


    def "블로그 조회시 Server Error 발생"() {
        given:
        def naverSearchSend = new NaverSearchSend()
        naverSearchSend.query = "맛집"
        naverSearchSend.sort = "sim"
        naverSearchSend.start = 10
        naverSearchSend.display = 100

        and:
        restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> { throw new HttpServerErrorException(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "error message") }

        when:
        naverClient.getSearchBlog(naverSearchSend)

        then:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.NAVER
        ex.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "블로그 조회시 기타 Error 발생"() {
        given:
        def naverSearchSend = new NaverSearchSend()
        naverSearchSend.query = "맛집"
        naverSearchSend.sort = "sim"
        naverSearchSend.start = 10
        naverSearchSend.display = 100

        and:
        restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> { throw new RuntimeException("error message") }

        when:
        naverClient.getSearchBlog(naverSearchSend)

        then:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.NAVER
        ex.httpStatus == HttpStatus.BAD_GATEWAY
    }
}
