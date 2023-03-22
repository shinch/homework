package com.gmail.shinch.search_blog.app.controller.blog

import com.gmail.shinch.search_blog.app.config.ExceptionControllerAdvice
import com.gmail.shinch.search_blog.app.event.search.SearchEngine
import com.gmail.shinch.search_blog.domain.blog.service.BlogDto
import com.gmail.shinch.search_blog.domain.blog.service.OrderType
import com.gmail.shinch.search_blog.domain.blog.service.SearchBlogResultDto
import com.gmail.shinch.search_blog.domain.blog.service.SearchDto
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header

class BlogV1ControllerTest extends Specification {
    def blogDelegator
    def blogAppMapper
    def mockMvc

    def setup() {
        blogDelegator = Mock(BlogDelegator)
        blogAppMapper = Mock(BlogAppMapper)
        mockMvc = MockMvcBuilders.standaloneSetup(new BlogV1Controller(blogDelegator, blogAppMapper))
                .setControllerAdvice(ExceptionControllerAdvice.class).build()
    }

    def "/api/v1/blog/contents/{keyword} 200"() {
        given:
        def keyword = "search-keyword"
        def orderType = "ACCURACY"
        def request = get("/api/v1/blog/contents/{keyword}", keyword)
                          .param("order_type", orderType)
                          .header(HttpHeaders.RANGE, "pages=1")
        and:
        def searchDto = new SearchDto()
        searchDto.keyword = keyword
        searchDto.orderType = OrderType.ACCURACY
        searchDto.page = 1
        searchDto.size = 10
        def blogDto = new BlogDto()
        blogDto.name = "블로그 이름"
        blogDto.link = "http://www.blog.com/test/2"
        blogDto.title = "블로그 제목"
        def searchBlogResultDto = new SearchBlogResultDto()
        searchBlogResultDto.blogs = List.of(blogDto)
        searchBlogResultDto.totalCount = 100
        def blogV1Response = new BlogV1Response()
        blogV1Response.name = "블로그 이름"
        blogV1Response.link = "http://www.blog.com/test/2"
        blogV1Response.title = "블로그 제목"
        def blogV1Responses = List.of(blogV1Response)

        and:
        1 * blogAppMapper.toSearchDto(keyword, orderType, _ as PageRangeInfo) >> searchDto
        1 * blogDelegator.getSearchResult(searchDto) >> searchBlogResultDto
        1 * blogAppMapper.toBlogV1Responses(searchBlogResultDto.getBlogs()) >> blogV1Responses
        when:
        def response = mockMvc.perform(request)

        then:
        response.andExpect(status().isOk())

        and:
        response.andExpect(header().string(HttpHeaders.CONTENT_RANGE, "pages=1/10"))
    }

    def "/api/v1/blog/contents/{keyword} 204"() {
        given:
        def keyword = "search-keyword"
        def orderType = "ACCURACY"
        def request = get("/api/v1/blog/contents/{keyword}", keyword)
                .param("order_type", orderType)
                .header(HttpHeaders.RANGE, "pages=1")
        and:
        def searchDto = new SearchDto()
        searchDto.keyword = keyword
        searchDto.orderType = OrderType.ACCURACY
        searchDto.page = 1
        searchDto.size = 10
        def searchBlogResultDto = new SearchBlogResultDto()
        searchBlogResultDto.blogs = List.of()
        searchBlogResultDto.totalCount = 100
        def blogV1Responses = List.of()

        and:
        1 * blogAppMapper.toSearchDto(keyword, orderType, _ as PageRangeInfo) >> searchDto
        1 * blogDelegator.getSearchResult(searchDto) >> searchBlogResultDto
        1 * blogAppMapper.toBlogV1Responses(searchBlogResultDto.getBlogs()) >> blogV1Responses
        when:
        def response = mockMvc.perform(request)

        then:
        response.andExpect(status().isNoContent())
    }

    def "/api/v1/blog/engine/{engine} 200"() {
        given:
        def engine = "KAKAO"
        def request = patch("/api/v1/blog/engine/{engine}", engine)

        and:
        1 * blogDelegator.setSearchEngine(SearchEngine.KAKAO)

        when:
        def response = mockMvc.perform(request)

        then:
        response.andExpect(status().isOk())

    }
}
