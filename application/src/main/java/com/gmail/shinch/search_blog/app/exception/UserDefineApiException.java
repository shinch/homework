package com.gmail.shinch.search_blog.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class UserDefineApiException extends RuntimeException {
    private HttpStatus httpstatus;
    private String message;

}
