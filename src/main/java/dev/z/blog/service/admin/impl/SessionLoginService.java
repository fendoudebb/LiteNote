package dev.z.blog.service.admin.impl;

import dev.z.blog.dto.request.Identity;
import dev.z.blog.dto.response.Credentials;
import dev.z.blog.service.admin.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Override
    public Credentials login(Identity identity) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(identity.password(), identity.password());
        authenticationManager.authenticate(token);
        return new Credentials("session");
    }
}
