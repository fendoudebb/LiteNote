package z.note.lite.web.advice.cipher.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import z.note.lite.web.advice.cipher.CipherService;
import z.note.lite.web.advice.cipher.annotation.Encode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
@Order // last response advice
public class EncodeResponseAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private CipherService cipherService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encode.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            try {
                body = new String(cipherService.encode(objectMapper.writeValueAsBytes(body)));
            } catch (JsonProcessingException e) {
                log.error("Encode ResponseAdvice Error: {}", e.getMessage(), e);
            }
        }
        return body;
    }
}
