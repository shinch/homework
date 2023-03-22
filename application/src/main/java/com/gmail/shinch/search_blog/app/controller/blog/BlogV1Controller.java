package com.gmail.shinch.search_blog.app.controller.blog;

import com.gmail.shinch.search_blog.app.event.search.SearchEngine;
import com.gmail.shinch.search_blog.app.exception.BadRequestException;
import com.gmail.shinch.search_blog.app.exception.NoContentException;
import com.gmail.shinch.search_blog.domain.blog.service.OrderType;
import com.gmail.shinch.search_blog.domain.blog.service.SearchBlogResultDto;
import com.gmail.shinch.search_blog.domain.blog.service.SearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blog")
@Tag(name = "블로그 검색", description = "외부 검색 엔진을 이용한 블로그 검색 apis")
@Validated
public class BlogV1Controller {
    private final BlogDelegator blogDelegator;
    private final BlogAppMapper blogAppMapper;

    @Operation(summary = "키워드 검색", description = "키워드를 포함하는 blog를 조회한다.")
    @GetMapping("/contents/{keyword}")
    public ResponseEntity<List<BlogV1Response>> getBlogs(
            @Parameter(description = "검색할 범위정보 : pages=보일페이지 번호 / 비어 있는 경우 page=1이 대입 됩니다.", example = "pages=1")
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeInfo,
            @Parameter(description = "검색할 Keyword", required = true, example = "맛집")
            @PathVariable(value = "keyword") String keyword,
            @Parameter(description = "정렬방법", required = true, example = OrderType.Constants.EXAMPLE, schema = @Schema(type = "string", allowableValues = {OrderType.Constants.ACCURACY, OrderType.Constants.NEWEST}) )
            @RequestParam(value = "order_type", required = false) @NotBlank(message = "정렬방법은 필수 입니다.") @Pattern(regexp="ACCURACY||NEWEST", message = "정렬방법에 허용되지 않는 정보 입니다.") String orderType ) {
        PageRangeInfo pageRangeInfo = PageRangeInfo.ofRequest(rangeInfo);
        SearchDto searchDto = blogAppMapper.toSearchDto(keyword, orderType, pageRangeInfo);

        SearchBlogResultDto searchBlogResultDto = blogDelegator.getSearchResult(searchDto);
        pageRangeInfo.setByTotalContents(searchBlogResultDto.getTotalCount());
        List<BlogV1Response> blogV1Responses = blogAppMapper.toBlogV1Responses(searchBlogResultDto.getBlogs());
        if ( blogV1Responses.size() == 0 ) {
            throw new NoContentException("요청 하신 검색 조건에 정보는 비어 있습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_RANGE, pageRangeInfo.getPageRangeResponse());
        return ResponseEntity.ok().headers(headers).body(blogV1Responses);
    }

    // Delegator 변경
    @Operation(summary = "검색 엔지 지정", description = "사용할 검색 엔지을 지정 한다.")
    @PatchMapping("/engine/{engine}")
    public void patchEngine(
            @Parameter(description = "사용할 검색 엔진", required = true, example = SearchEngine.Constants.EXAMPLE, schema = @Schema(type = "string", allowableValues = {SearchEngine.Constants.KAKAO, SearchEngine.Constants.NAVER}))
            @PathVariable("engine") @Pattern(regexp="KAKAO||NAVER", message = "허용되지 않는 검색엔진 정보 입니다.") String engine ) {
        blogDelegator.setSearchEngine(SearchEngine.valueOf(engine));
    }
}
