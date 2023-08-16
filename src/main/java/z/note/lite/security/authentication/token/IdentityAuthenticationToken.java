package z.note.lite.security.authentication.token;

import z.note.lite.dto.request.Identity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Setter
@Getter
public class IdentityAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String captcha;

    public IdentityAuthenticationToken(Identity identity) {
        super(identity.getUsername(), identity.getPassword());
        this.captcha = identity.getCaptcha();
    }

}
