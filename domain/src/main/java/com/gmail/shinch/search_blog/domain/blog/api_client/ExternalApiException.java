package com.gmail.shinch.search_blog.domain.blog.api_client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ExternalApiException extends RuntimeException {
    private ClientType clientType;
    private HttpStatus httpStatus;
    private String message;
}
