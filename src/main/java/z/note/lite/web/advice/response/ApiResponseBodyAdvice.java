package z.note.lite.web.advice.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.slf4j.MDC;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import z.note.lite.web.Endpoint;
import z.note.lite.web.filter.trace.TraceFilter;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private WebEndpointProperties webEndpointProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() != Response.class && returnType.getParameterType() != ProblemDetail.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String contextPath = request.getURI().getPath();
        if (!contextPath.startsWith(Endpoint.Api.CONTEXT) && !contextPath.startsWith(webEndpointProperties.getBasePath())) {
            return body;
        }
        Response res = Response.builder().requestId(MDC.get(TraceFilter.TRACE_ID)).data(body).build();
        // StringHttpMessageConverter
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(res);
            } catch (Exception ignore) {
            }
        }
        return res;
    }
}
