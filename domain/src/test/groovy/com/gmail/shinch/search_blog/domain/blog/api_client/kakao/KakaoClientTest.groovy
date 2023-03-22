package com.gmail.shinch.search_blog.domain.blog.api_client.kakao

import com.gmail.shinch.search_blog.domain.blog.api_client.ClientType
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException
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

class KakaoClientTest extends Specification {
    def kakaoClient
    def apiHost
    def apiKey
    def restTemplate

    def setup() {
        apiHost = "dapi.kakao.com"
        apiKey = "b905c3938904dd33b8cde6756a967a16"
        restTemplate = Mock(RestTemplate)
        kakaoClient = new KakaoClient( apiHost, apiKey, restTemplate )
    }

    def "블로그 조회 성공"() {
        given:
        def kakaoSearchSend = new KakaoSearchSend()
        kakaoSearchSend.setQuery("맛집")
        kakaoSearchSend.setSort("accuracy")
        kakaoSearchSend.setPage(1)
        kakaoSearchSend.setSize(5)

        def blogMeta = new BlogMeta()
        blogMeta.end = true
        blogMeta.pageableCount = 10
        blogMeta.totalCount = 100
        def kakaoSearchReceive = new KakaoSearchReceive()
        kakaoSearchReceive.documents = List.of()
        kakaoSearchReceive.meta = blogMeta

        def response = new ResponseEntity(kakaoSearchReceive, HttpStatusCode.valueOf(200))

        when:
        def checkData = kakaoClient.getSearchBlog(kakaoSearchSend)

        then:
        1 * restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> response
        checkData.documents.size() == kakaoSearchReceive.documents.size()
        checkData.meta.totalCount == blogMeta.totalCount
    }

    def "블로그 조회시 Client Error 발생"() {
        given:
        def kakaoSearchSend = new KakaoSearchSend()
        kakaoSearchSend.setQuery("맛집")
        kakaoSearchSend.setSort("accuracy")
        kakaoSearchSend.setPage(1)
        kakaoSearchSend.setSize(5)

        and:
        restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> { throw new HttpClientErrorException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), "error message") }

        when:
        kakaoClient.getSearchBlog(kakaoSearchSend)

        then:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.KAKAO
        ex.httpStatus == HttpStatus.BAD_REQUEST
    }


    def "블로그 조회시 Server Error 발생"() {
        given:
        def kakaoSearchSend = new KakaoSearchSend()
        kakaoSearchSend.setQuery("맛집")
        kakaoSearchSend.setSort("accuracy")
        kakaoSearchSend.setPage(1)
        kakaoSearchSend.setSize(5)

        and:
        restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> { throw new HttpServerErrorException(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "error message") }

        when:
        kakaoClient.getSearchBlog(kakaoSearchSend)

        then:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.KAKAO
        ex.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "블로그 조회시 기타 Error 발생"() {
        given:
        def kakaoSearchSend = new KakaoSearchSend()
        kakaoSearchSend.setQuery("맛집")
        kakaoSearchSend.setSort("accuracy")
        kakaoSearchSend.setPage(1)
        kakaoSearchSend.setSize(5)

        and:
        restTemplate.exchange(_ as URI, HttpMethod.GET, _ as HttpEntity, _ as ParameterizedTypeReference) >> { throw new RuntimeException("error message") }

        when:
        kakaoClient.getSearchBlog(kakaoSearchSend)

        then:
        def ex = thrown(ExternalApiException)
        ex.clientType == ClientType.KAKAO
        ex.httpStatus == HttpStatus.BAD_GATEWAY
    }
}
