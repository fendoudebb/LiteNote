package z.note.lite.config.advice.cipher.advice;

import jakarta.annotation.Resource;
import z.note.lite.config.advice.cipher.CipherService;
import z.note.lite.config.advice.cipher.annotation.Decode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DecodeRequestAdvice extends RequestBodyAdviceAdapter {

    @Resource
    private CipherService cipherService;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(Decode.class) || methodParameter.hasParameterAnnotation(Decode.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            byte[] body = inputMessage.getBody().readAllBytes();
            byte[] decode = cipherService.decode(body);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return new ByteArrayInputStream(decode);
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (IOException e) {
            log.error("Decode RequestBody Error: {}", e.getMessage(), e);
        }
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }
}
