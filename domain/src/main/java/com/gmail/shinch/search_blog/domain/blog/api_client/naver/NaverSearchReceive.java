package com.gmail.shinch.search_blog.domain.blog.api_client.naver;

import lombok.Data;

import java.util.List;

@Data
public class NaverSearchReceive {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBlogItem> items;
}
