package com.gmail.shinch.search_blog.domain.keyword.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeywordDto {
    private Long seq; //자동 증가 일련번호
    private String keyword; //검색어
    private Integer cnt; //조회 횟수
}
