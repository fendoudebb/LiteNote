package dev.z.blog.security.authentication.token;

import dev.z.blog.dto.request.Identity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Setter
@Getter
public class IdentityAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String captcha;

    public IdentityAuthenticationToken(Identity identity) {
        super(identity.username(), identity.password());
        this.captcha = identity.captcha();
    }

}
