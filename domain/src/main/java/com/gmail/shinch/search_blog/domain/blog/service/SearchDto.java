package com.gmail.shinch.search_blog.domain.blog.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {
    private String keyword;
    private OrderType orderType;
    private int page;
    private int size;
}
