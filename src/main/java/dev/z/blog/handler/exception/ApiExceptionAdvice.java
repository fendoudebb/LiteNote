package dev.z.blog.handler.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler
    public ProblemDetail onIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("Illegal Argument: {} {}", request.getMethod(), request.getRequestURL(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Illegal Argument");
        problemDetail.setType(URI.create(request.getRequestURI()));
        return problemDetail;
    }

}
