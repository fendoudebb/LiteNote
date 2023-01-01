package z.lite.note.service.admin.impl;

import z.lite.note.security.authentication.token.IdentityAuthenticationToken;
import z.lite.note.dto.request.Identity;
import z.lite.note.dto.response.Credentials;
import z.lite.note.service.admin.LoginService;
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
