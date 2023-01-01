package z.lite.note.service.admin.impl;

import z.lite.note.dto.request.Identity;
import z.lite.note.dto.response.Credentials;
import z.lite.note.service.admin.LoginService;
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
