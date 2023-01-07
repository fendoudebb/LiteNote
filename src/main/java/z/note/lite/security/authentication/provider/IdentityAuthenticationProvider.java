package z.note.lite.security.authentication.provider;

import z.note.lite.infra.Cache;
import z.note.lite.security.authentication.exception.CaptchaMismatchException;
import z.note.lite.security.authentication.token.IdentityAuthenticationToken;
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
            String key = Cache.Prefix.CAPTCHA + token.getPrincipal();
            Object issueCaptcha = cache.get(key);
            String requestCaptcha = token.getCaptcha();
            cache.remove(key);
            if (!Objects.equals(requestCaptcha, issueCaptcha)) {
                throw new CaptchaMismatchException(String.format("Mismatched Captcha, Issued: %s, Requested: %s", issueCaptcha, requestCaptcha));
            }
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
