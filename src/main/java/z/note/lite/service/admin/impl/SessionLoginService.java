package z.note.lite.service.admin.impl;

import z.note.lite.security.authentication.token.IdentityAuthenticationToken;
import z.note.lite.dto.request.Identity;
import z.note.lite.dto.response.Credentials;
import z.note.lite.service.admin.LoginService;
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
