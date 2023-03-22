package com.gmail.shinch.search_blog.app.event;

import com.gmail.shinch.search_blog.app.event.keyword.KeywordEvent;
import com.gmail.shinch.search_blog.app.event.search.SearchErrorEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(final KeywordEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    public void publish(final SearchErrorEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
