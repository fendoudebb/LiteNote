package z.note.lite.security.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import z.note.lite.config.preferences.AdminProperties;

import java.util.Objects;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class AdminAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AdminProperties adminProperties;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Authentication auth = authentication.get();
        if (Objects.equals(auth.getPrincipal(), adminProperties.supervisor())) {
            return new AuthorizationDecision(true);
        }
        // TODO Permission check
        return new AuthorizationDecision(false);
    }
}
