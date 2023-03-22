package com.gmail.shinch.search_blog.domain.blog.api_client.kakao;

import lombok.Data;

@Data
public class BlogDocument {
    private String blogname;
    private String contents;
    private String datetime;
    private String thumbnail;
    private String title;
    private String url;
}
