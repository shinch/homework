package com.gmail.shinch.search_blog.domain.blog.service;

public interface SearchService {
    SearchBlogResultDto searchBlogByKeyword(SearchDto searchDto);
}
