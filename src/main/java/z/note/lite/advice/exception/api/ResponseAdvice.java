package z.note.lite.advice.exception.api;

import z.note.lite.advice.response.Response;
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

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseAdvice/* extends ResponseEntityExceptionHandler */{

    private final MessageSource messageSource;

    @ExceptionHandler
    public Response onCaptchaMismatchException(CaptchaMismatchException e, HttpServletRequest request) {
        log.error("Method: {} URI: {} Error: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        String message = messageSource.getMessage("captcha_mismatch", null, LocaleContextHolder.getLocale());
        return Response.builder().code(-1).msg(message).build();
    }

}
