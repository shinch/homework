package com.gmail.shinch.search_blog.domain.blog.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    ACCURACY("정확도", "accuracy", "sim"),
    NEWEST("최신순", "recency", "date");

    String description;
    String kakaoType;
    String naverType;

    public static class Constants {
        public static final String EXAMPLE = "ACCURACY";
        public static final String ACCURACY = "ACCURACY";
        public static final String NEWEST = "NEWEST";
    }
}
