package com.gmail.shinch.search_blog.app.controller.blog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "blog 조회 결과 정보")
public class BlogV1Response {
    @Schema(description = "blog 이름", required = true, example = "입사 시험")
    private String name;
    @Schema(description = "게시물 link", required = true, example = "https://recruit.kakaobank.com/jobs")
    private String link;
    @Schema(description = "게시물 제목", required = true, example = "카카오 뱅크 사전 과제")
    private String title;
}
