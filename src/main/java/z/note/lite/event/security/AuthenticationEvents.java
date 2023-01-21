package z.note.lite.event.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

// https://docs.spring.io/spring-security/reference/servlet/authentication/events.html
@Slf4j
@Component
public class AuthenticationEvents {
	@EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
		// ...
        log.info("authentication success#{}", success);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
		// ...
        log.info("authentication failure#{}", failures);
    }
}
