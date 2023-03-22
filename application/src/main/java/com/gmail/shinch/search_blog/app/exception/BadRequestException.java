package com.gmail.shinch.search_blog.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {
    private String message;
    private String example;
}
