package dev.z.blog.service.admin.impl;

import dev.z.blog.security.authentication.token.IdentityAuthenticationToken;
import dev.z.blog.dto.request.Identity;
import dev.z.blog.dto.response.Credentials;
import dev.z.blog.service.admin.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;

@Slf4j
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Override
    public Credentials login(Identity identity) {
        IdentityAuthenticationToken token = new IdentityAuthenticationToken(identity);
        authenticationManager.authenticate(token);
        return new Credentials("session");
    }
}
