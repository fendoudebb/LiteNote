package z.note.lite.web.security.authentication.provider;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import z.note.lite.infra.Cache;
import z.note.lite.web.security.authentication.exception.CaptchaMismatchException;
import z.note.lite.web.security.authentication.token.IdentityAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class IdentityAuthenticationProvider extends DaoAuthenticationProvider {

    private final Cache cache;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication instanceof IdentityAuthenticationToken token) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String key = Cache.Prefix.CAPTCHA + request.getSession().getId();
            Object issueCaptcha = cache.get(key);
            String requestCaptcha = token.getCaptcha();
            if (!Objects.equals(requestCaptcha, issueCaptcha)) {
                throw new CaptchaMismatchException(String.format("Mismatched Captcha, Issued: %s, Requested: %s", issueCaptcha, requestCaptcha));
            }
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
