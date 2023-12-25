package z.note.lite.service.api.impl;

import z.note.lite.web.http.request.Identity;
import z.note.lite.web.http.response.Credentials;
import z.note.lite.service.api.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtLoginService implements LoginService {

    @Override
    public Credentials login(Identity identity) {
//        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(identity.password(), identity.password());
//        authenticationManager.authenticate(token);

        return new Credentials("jwt");
    }
}
