package com.gmail.shinch.search_blog.domain.blog.api_client.kakao;

import lombok.Data;

import java.util.List;

@Data
public class KakaoSearchReceive {
    private List<BlogDocument> documents;
    private BlogMeta meta;
}
