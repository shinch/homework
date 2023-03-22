package com.gmail.shinch.search_blog.app.event.search;

import com.gmail.shinch.search_blog.app.controller.blog.BlogDelegator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchErrorEventListener {
    private final BlogDelegator blogDelegator;

    private int checkHour = LocalTime.now().getHour();
    private int errorCnt = 0;
    private int KILL_CNT = 10;
    @EventListener
    @Async("searchErrorEventExecutor")
    public void searchError(SearchErrorEvent event) {
        if ( LocalTime.now().getHour() == checkHour ) {
            errorCnt ++;
        } else {
            checkHour = LocalTime.now().getHour();
            errorCnt = 1;
        }
        if ( errorCnt > KILL_CNT ) {
            switch (event.getSearchEngine()) {
                case KAKAO -> blogDelegator.setSearchEngine(SearchEngine.NAVER);
                case NAVER -> blogDelegator.setSearchEngine(SearchEngine.KAKAO);
            }
            errorCnt = 0;
        }
    }
}
