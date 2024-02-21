package z.note.lite.service.api.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import z.note.lite.infra.Cache;
import z.note.lite.mapper.api.SysUserMapper;
import z.note.lite.entity.SysUser;
import z.note.lite.config.security.authentication.exception.CaptchaMismatchException;
import z.note.lite.request.Identity;
import z.note.lite.response.Credentials;
import z.note.lite.service.api.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import z.note.lite.config.security.authentication.exception.CredentialsErrorException;
import z.note.lite.config.security.authentication.token.SysUserAuthenticationToken;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final SysUserMapper sysUserMapper;

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
        SysUser sysUser = sysUserMapper.findByUsername(identity.getUsername());
        if (Objects.isNull(sysUser) || !Objects.equals(identity.getPassword(), sysUser.getPassword())) {
            throw new CredentialsErrorException(String.format("Error Username or Password, Username: %s, Password: %s", identity.getUsername(), identity.getPassword()));
        }
        SysUserAuthenticationToken token = new SysUserAuthenticationToken(sysUser, AuthorityUtils.NO_AUTHORITIES);
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
