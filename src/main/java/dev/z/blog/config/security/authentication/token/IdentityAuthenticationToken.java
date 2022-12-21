package dev.z.blog.config.security.authentication.token;

import dev.z.blog.dto.request.Identity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class IdentityAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public IdentityAuthenticationToken(Identity identity) {
        super(identity.username(), identity.password());
    }

}
