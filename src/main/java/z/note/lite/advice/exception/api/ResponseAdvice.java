package z.note.lite.advice.exception.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import z.note.lite.advice.response.Response;
import z.note.lite.infra.exception.RateLimitationException;
import z.note.lite.security.authentication.exception.CaptchaMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import z.note.lite.util.IpUtils;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseAdvice/* extends ResponseEntityExceptionHandler */{

    private final MessageSource messageSource;

    @ExceptionHandler({
            CaptchaMismatchException.class,
            RateLimitationException.class
    })
    public Response onCaptchaMismatchException(Throwable ex, HttpServletRequest request) {
        log.error("User: {} Method: {} URI: {} Error: {}", getUser(), request.getMethod(), request.getRequestURI(), ex.getMessage());
        String code;
        if (ex instanceof CaptchaMismatchException) {
            code = "captcha_mismatch";
        } else if (ex instanceof RateLimitationException) {
            code = "rate_limitation";
        } else {
            code = "system_error";
        }
        String message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        return Response.builder().code(-1).msg(message).build();
    }

    private String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user;
        if (Objects.isNull(authentication)) {
            user = IpUtils.getIp();
        } else {
            user = (String) authentication.getPrincipal();
        }
        return user;
    }

}
