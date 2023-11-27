package z.note.lite.web.advice.exception.api;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ResponseStatus;
import z.note.lite.web.advice.response.Response;
import z.note.lite.infra.exception.RateLimitationException;
import z.note.lite.web.security.authentication.exception.CaptchaMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import z.note.lite.infra.RequestUtils;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseAdvice/* extends ResponseEntityExceptionHandler */{

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response onIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("IllegalArgument User: {} Method: {} URI: {} Error: {}", getUser(), request.getMethod(), request.getRequestURI(), e.getMessage());
        String message = messageSource.getMessage("illegal_argument", null, LocaleContextHolder.getLocale());
        return Response.builder().code(-1).msg(message).build();
    }

    @ExceptionHandler(CaptchaMismatchException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response onCaptchaMismatchException(Exception e, HttpServletRequest request) {
        log.error("CaptchaMismatch User: {} Method: {} URI: {} Error: {}", getUser(), request.getMethod(), request.getRequestURI(), e.getMessage());
        String message = messageSource.getMessage("captcha_mismatch", null, LocaleContextHolder.getLocale());
        return Response.builder().code(-1).msg(message).build();
    }

    @ExceptionHandler(RateLimitationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response onRateLimitationException(Exception e, HttpServletRequest request) {
        log.error("RateLimit User: {} Method: {} URI: {} Error: {}", getUser(), request.getMethod(), request.getRequestURI(), e.getMessage());
        String message = messageSource.getMessage("rate_limitation", null, LocaleContextHolder.getLocale());
        return Response.builder().code(-1).msg(message).build();
    }

    private String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user;
        if (Objects.isNull(authentication)) {
            user = RequestUtils.getIp();
        } else {
            user = (String) authentication.getPrincipal();
        }
        return user;
    }

}
