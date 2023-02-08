package z.note.lite.event.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Slf4j
@Component
public class RequestHandledEvents {
	@EventListener
    public void requestHandledEvent(RequestHandledEvent event) {
		// ...
        log.info("RequestHandledEvent#{}", event);
    }

    @EventListener
    public void servletRequestHandledEvent(ServletRequestHandledEvent event) {
		// ...
        log.info("ServletRequestHandledEvent#{}", event);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
    }
}
