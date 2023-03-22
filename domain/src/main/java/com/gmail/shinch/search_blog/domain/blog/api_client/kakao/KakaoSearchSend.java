package com.gmail.shinch.search_blog.domain.blog.api_client.kakao;

import lombok.Data;

@Data
public class KakaoSearchSend {
    private String query;
    private String sort;
    private int page;
    private int size;
}
