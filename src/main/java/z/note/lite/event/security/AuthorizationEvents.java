package z.note.lite.event.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationEvents {

    @EventListener
    public void onSuccess(AuthorizationGrantedEvent<?> success) {
        // ...
        log.info("authorization granted event#{}", success);
    }

    @EventListener
    public void onFailure(AuthorizationDeniedEvent<?> failure) {
        // ...
        log.info("authorization denied event#{}", failure);
    }

}
