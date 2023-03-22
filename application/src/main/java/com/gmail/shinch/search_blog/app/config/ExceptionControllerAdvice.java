package com.gmail.shinch.search_blog.app.config;

import com.gmail.shinch.search_blog.app.exception.BadRequestException;
import com.gmail.shinch.search_blog.app.exception.NoContentException;
import com.gmail.shinch.search_blog.app.exception.UserDefineApiException;
import com.gmail.shinch.search_blog.domain.blog.api_client.ExternalApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {
    //204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    public void noContent(NoContentException ex) {
        log.info("{}", ex.getMessage());
    }

    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BadRequestResponse badRequest(HttpMessageNotReadableException ex) {
        log.info("ERROR01 : {}", ex);
        BadRequestResponse badRequestResponse = new BadRequestResponse();
        badRequestResponse.setMessages(List.of(ex.getMessage()));
        return badRequestResponse;
    }

    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public BadRequestResponse badRequest(ServletRequestBindingException ex) {
        log.info("ERROR02 : {}", ex);
        BadRequestResponse badRequestResponse = new BadRequestResponse();
        badRequestResponse.setMessages(List.of(ex.getMessage()));
        return badRequestResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public BadRequestResponse badRequest(MethodArgumentTypeMismatchException ex) {
        log.info("ERROR03 : {}", ex);
        BadRequestResponse badRequestResponse = new BadRequestResponse();
        badRequestResponse.setMessages(List.of(ex.getName() + " 형식이 잘못 되었습니다."));
        return badRequestResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BadRequestResponse badRequest(MethodArgumentNotValidException ex) {
        log.info("ERROR04 : {}", ex);
        List<String> messages = new ArrayList();
        for ( ObjectError errorObj : ex.getBindingResult().getAllErrors() ) {
            messages.add(errorObj.getDefaultMessage());
        }
        BadRequestResponse badRequestResponse = new BadRequestResponse();
        badRequestResponse.setMessages(messages);
        return badRequestResponse;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BadRequestResponse badRequest(ConstraintViolationException ex) {
        log.info("ERROR05 : {}", ex);
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations ) {
            log.info("violation : {}", violation);
            messages.add(violation.getMessage());
        }
        BadRequestResponse badRequestResponse = new BadRequestResponse();
        badRequestResponse.setMessages(messages);
        return badRequestResponse;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public BadRequestResponse badRequest(BadRequestException ex) {
        log.info("ERROR06 : {}", ex);
        BadRequestResponse badRequestResponse = new BadRequestResponse();
        badRequestResponse.setMessages(List.of(String.format("$s ex)$s", ex.getMessage(), ex.getExample())));
        return badRequestResponse;
    }

    //401 Unauthorized
    //402 Payment Required
    //403 Forbidden
    //404
    //405
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void methodNotAllowed(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.warn("허용 되지 않는 http method[{} {}] 입니다.{}", request.getMethod(), request.getRequestURI(), ex.getMessage());
    }
    //406 Not Acceptable
    //407 Proxy Authentication Required
    //408 Request Timeout
    //409 Conflict
    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void dbDuplicateError(DataIntegrityViolationException ex) {
        log.error("사용 데이타 충돌이 발생 하였습니다.", ex);
    }

    //410 Gone
    //411 Length Required
    //412 Precondition Failed
    //413 Request Entity Too Large
    //414 Request-URI Too Long
    //415
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void unsupportedMediaType(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
        log.warn("지원 되지 않는 미디어 형식[{} {}] 입니다.{}", request.getMethod(), request.getRequestURI(), ex.getMessage());
    }

    //416 Requested range not satisfiable
    //417 Expectation Failed

    //500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void internalServerError(Exception ex) {
        log.error("확인되지 않은 에러가 발생 하였습니다.", ex);
    }

    //501 Not implemented
    //502
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(ExternalApiException.class)
    public void badGateway(HttpServletRequest request, HttpServletResponse response, ExternalApiException ex) {
        log.warn("외부[{}] 통신시[{}] 에러가[{}] 발생 하였습니다.", ex.getClientType(), ex.getHttpStatus(), ex.getMessage());
    }
    //503 Service Unavailable
    //504 Gateway Timeout
    //505 HTTP Version Not Supported

    //가변 처리
    @ExceptionHandler(UserDefineApiException.class)
    public void settlementRuntime(HttpServletResponse response, UserDefineApiException ex) {
        response.setStatus(ex.getHttpstatus().value());
        log.warn("사용자 정의 에러[{} / {}]가 발생 하였습니다.", ex.getHttpstatus(), ex.getMessage());
    }}
