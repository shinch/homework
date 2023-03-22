package com.gmail.shinch.search_blog.app.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "400 Error 반환 Object")
public class BadRequestResponse {
    @Schema(description = "에러 Message 목록", required = true, example = "[\"필수값이 누락되었습니다.\"]")
    private List<String> messages;
}
