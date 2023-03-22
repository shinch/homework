package com.gmail.shinch.search_blog.app.event.keyword;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class KeywordEvent extends ApplicationEvent {
    private String keyword;

    public KeywordEvent(Object source, String keyword) {
        super(source);
        this.keyword = keyword;
    }
}
