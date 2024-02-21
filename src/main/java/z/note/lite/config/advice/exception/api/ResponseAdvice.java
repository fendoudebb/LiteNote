package z.note.lite.config.advice.exception.api;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import z.note.lite.config.advice.response.Response;
import z.note.lite.infra.exception.RateLimitationException;
import z.note.lite.config.security.authentication.exception.CaptchaMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import z.note.lite.infra.RequestUtils;
import z.note.lite.config.security.authentication.exception.CredentialsErrorException;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseAdvice/* extends ResponseEntityExceptionHandler */{

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler
    public Response onMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String msg = e.getFieldErrors().stream().map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage()).collect(Collectors.joining(","));
        log.error("[advice][MethodArgumentNotValidException] {}: {}, {}: {}", request.getMethod(), request.getRequestURI(), e.getObjectName(), msg);
        return Response.builder().code(-1).msg("系统异常").build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response onIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("[advice][IllegalArgumentException] {}, {}: {}, Error: {}", getUser(), request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        String message = messageSource.getMessage("illegal_argument", null, LocaleContextHolder.getLocale());
        return Response.builder().code(-1).msg(message).build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response onAuthenticationException(Exception e, HttpServletRequest request) {
        String message = null;
        if (e instanceof CaptchaMismatchException) {
            message = messageSource.getMessage("captcha_mismatch", null, LocaleContextHolder.getLocale());
        } else if (e instanceof CredentialsErrorException) {
            message = messageSource.getMessage("credentials_error", null, LocaleContextHolder.getLocale());
        }
        log.error("[advice][AuthenticationException] {}, {}: {}, Error: {}", getUser(), request.getMethod(), request.getRequestURI(), e.getMessage());
        return Response.builder().code(-1).msg(message).build();
    }

    @ExceptionHandler(RateLimitationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response onRateLimitationException(Exception e, HttpServletRequest request) {
        log.error("[advice][RateLimitationException] {}, {}: {}, Error: {}", getUser(), request.getMethod(), request.getRequestURI(), e.getMessage());
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
