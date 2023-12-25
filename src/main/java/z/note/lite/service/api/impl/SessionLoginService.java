package z.note.lite.service.api.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import z.note.lite.infra.Cache;
import z.note.lite.repository.api.SysUserRepository;
import z.note.lite.web.model.admin.SysUser;
import z.note.lite.web.security.authentication.exception.CaptchaMismatchException;
import z.note.lite.web.http.request.Identity;
import z.note.lite.web.http.response.Credentials;
import z.note.lite.service.api.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import z.note.lite.web.security.authentication.exception.CredentialsErrorException;
import z.note.lite.web.security.authentication.token.IdentityAuthenticationToken;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final SysUserRepository sysUserRepository;

    private final Cache cache;

    @Override
    public Credentials login(Identity identity) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        Object issueCaptcha = cache.get(Cache.Prefix.CAPTCHA + session.getId());
        String requestCaptcha = identity.getCaptcha();
        if (!Objects.equals(requestCaptcha, issueCaptcha)) {
            throw new CaptchaMismatchException(String.format("Mismatched Captcha, Issued: %s, Requested: %s", issueCaptcha, requestCaptcha));
        }
        SysUser sysUser = sysUserRepository.findByUsername(identity.getUsername());
        if (Objects.isNull(sysUser) || !Objects.equals(identity.getPassword(), sysUser.getPassword())) {
            throw new CredentialsErrorException(String.format("Error Username or Password, Username: %s, Password: %s", identity.getUsername(), identity.getPassword()));
        }
        IdentityAuthenticationToken token = new IdentityAuthenticationToken(sysUser.getUsername(), AuthorityUtils.NO_AUTHORITIES);
        session.setAttribute("token", token);
        return new Credentials("session");
    }

    @Override
    public void logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession(false);
        if (Objects.nonNull(session)) {
            session.removeAttribute("token");
        }
    }
}
