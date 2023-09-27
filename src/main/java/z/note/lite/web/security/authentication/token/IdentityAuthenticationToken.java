package z.note.lite.web.security.authentication.token;

import lombok.ToString;
import z.note.lite.web.http.request.Identity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Setter
@Getter
@ToString
public class IdentityAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String captcha;

    public IdentityAuthenticationToken(Identity identity) {
        super(identity.getUsername(), identity.getPassword());
        this.captcha = identity.getCaptcha();
    }

}
