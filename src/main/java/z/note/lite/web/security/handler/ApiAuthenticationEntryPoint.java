package z.note.lite.web.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import z.note.lite.web.Endpoint;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("ApiAuthenticationEntryPoint commence");
        if (Objects.equals(request.getRequestURI(), Endpoint.Admin.CONTEXT)) {
            response.sendRedirect(Endpoint.Admin.LOGIN);
            return;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
