package z.note.lite.service.api.impl;

import z.note.lite.dto.request.Identity;
import z.note.lite.dto.response.Credentials;
import z.note.lite.service.api.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;

@Slf4j
@RequiredArgsConstructor
public class JwtLoginService implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Override
    public Credentials login(Identity identity) {
//        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(identity.password(), identity.password());
//        authenticationManager.authenticate(token);

        return new Credentials("jwt");
    }
}
