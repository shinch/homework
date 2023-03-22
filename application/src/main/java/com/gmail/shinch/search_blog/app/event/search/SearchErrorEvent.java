package com.gmail.shinch.search_blog.app.event.search;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.http.HttpStatus;

@Getter
public class SearchErrorEvent extends ApplicationEvent {
    // TODO : https://wildeveloperetrain.tistory.com/217
    private SearchEngine searchEngine;
    private HttpStatus httpStatus;

    public SearchErrorEvent(Object source, SearchEngine searchEngine, HttpStatus httpStatus) {
        super(source);
        this.searchEngine = searchEngine;
        this.httpStatus = httpStatus;
    }

}
