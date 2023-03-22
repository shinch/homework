package com.gmail.shinch.search_blog.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.gmail.shinch.search_blog"})
public class SearchBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchBlogApplication.class, args);
    }
}
