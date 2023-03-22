package com.gmail.shinch.search_blog.app.controller.keyword;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "조회 키워드 정보")
public class KeywordV1Response {
    @Schema(description = "등록 일련번호", required = true, example = "100")
    private Long seq; //자동 증가 일련번호
    @Schema(description = "검색어", required = true, example = "맛집")
    private String keyword; //검색어
    @Schema(description = "조회 횟수", required = true, example = "132")
    private Integer cnt; //조회 횟수
}
