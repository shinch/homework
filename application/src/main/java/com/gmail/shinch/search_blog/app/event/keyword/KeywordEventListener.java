package com.gmail.shinch.search_blog.app.event.keyword;

import com.gmail.shinch.search_blog.domain.keyword.service.KeywordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KeywordEventListener {

    private final KeywordService keywordService;

    @EventListener
    @Async("keywordEventExecutor")
    public void searchKeyword(KeywordEvent event) {
        keywordService.increaseCount(event.getKeyword());
    }
}
