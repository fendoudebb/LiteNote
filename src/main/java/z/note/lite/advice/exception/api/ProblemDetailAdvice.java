package z.note.lite.advice.exception.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@Slf4j
@RestControllerAdvice
@Order
public class ProblemDetailAdvice/* extends ResponseEntityExceptionHandler */{

    @ExceptionHandler
    public ProblemDetail onIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("Illegal Argument: {} {}", request.getMethod(), request.getRequestURL(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Illegal Argument");
        problemDetail.setType(URI.create(request.getRequestURI()));
        return problemDetail;
    }

    @ExceptionHandler
    public ProblemDetail onException(Exception e, HttpServletRequest request) {
        log.error("Not Catch Exception: {} {}", request.getMethod(), request.getRequestURL(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create(request.getRequestURI()));
        return problemDetail;
    }

}
