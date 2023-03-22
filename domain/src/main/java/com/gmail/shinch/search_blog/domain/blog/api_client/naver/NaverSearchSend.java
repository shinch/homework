package com.gmail.shinch.search_blog.domain.blog.api_client.naver;

import lombok.Data;

@Data
public class NaverSearchSend {
    private String query;
    private String sort;
    private int start;
    private int display;

}
