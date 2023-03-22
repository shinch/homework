package com.gmail.shinch.search_blog.app.controller.keyword

import com.gmail.shinch.search_blog.app.config.ExceptionControllerAdvice
import com.gmail.shinch.search_blog.domain.keyword.service.KeywordDto
import com.gmail.shinch.search_blog.domain.keyword.service.KeywordService
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class KeywordV1ControllerTest extends Specification {
    def keywordAppMapper
    def keywordService
    def mockMvc

    def setup() {
        keywordAppMapper = Mock(KeywordAppMapper)
        keywordService = Mock(KeywordService)
        mockMvc = MockMvcBuilders.standaloneSetup(new KeywordV1Controller(keywordAppMapper, keywordService))
                .setControllerAdvice(ExceptionControllerAdvice.class).build()
    }

    def "/api/v1/keyword/top-keywords 200"() {
        given:
        def request = get("/api/v1/keyword/top-keywords")

        and:
        def keywordDto = new KeywordDto()
        def keywordDtos = List.of(keywordDto)
        def KeywordV1Response = new KeywordV1Response()
        def keywordV1Responses = List.of(KeywordV1Response)

        and:
        1 * keywordService.getTopTeenKeyword() >> keywordDtos
        1 * keywordAppMapper.toKeywordV1Responses(keywordDtos) >> keywordV1Responses

        when:
        def response = mockMvc.perform(request)

        then:
        response.andExpect(status().isOk())

    }

    def "/api/v1/keyword/top-keywords 204"() {
        given:
        def request = get("/api/v1/keyword/top-keywords")

        and:
        def keywordDtos = List.of()
        def keywordV1Responses = List.of()

        and:
        1 * keywordService.getTopTeenKeyword() >> keywordDtos
        1 * keywordAppMapper.toKeywordV1Responses(keywordDtos) >> keywordV1Responses

        when:
        def response = mockMvc.perform(request)

        then:
        response.andExpect(status().isNoContent())

    }

}
