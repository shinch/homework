package com.gmail.shinch.search_blog.domain.blog.service;

import lombok.Data;

import java.util.List;

@Data
public class SearchBlogResultDto {
    private List<BlogDto> blogs;
    private int totalCount;
}
