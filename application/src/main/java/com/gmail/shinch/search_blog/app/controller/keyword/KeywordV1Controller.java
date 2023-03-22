package com.gmail.shinch.search_blog.app.controller.keyword;

import com.gmail.shinch.search_blog.app.exception.NoContentException;
import com.gmail.shinch.search_blog.domain.keyword.service.KeywordDto;
import com.gmail.shinch.search_blog.domain.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/keyword")
@Tag(name = "상위 조회 키워드 검색", description = "상위 조회 키워드 관련 apis")
public class KeywordV1Controller {
    private final KeywordAppMapper keywordAppMapper;
    private final KeywordService keywordService;
    // 상위 10개 조회
    @Operation(summary = "상위 검색 키워드 조회", description = "많이 사용된 검색 키워드 10개를 조회한다.")
    @GetMapping("/top-keywords")
    public List<KeywordV1Response> getTopKeyword() {
        List<KeywordDto> keywordDtos = keywordService.getTopTeenKeyword();
        List<KeywordV1Response> keywordV1Responses = keywordAppMapper.toKeywordV1Responses(keywordDtos);
        if ( keywordV1Responses.size() == 0 ) {
            throw new NoContentException("요청 하신 검색 조건에 정보는 비어 있습니다.");
        }
        return keywordV1Responses;
    }

}
