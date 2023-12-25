package z.note.lite.web.security.authentication.token;

import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@ToString
public class IdentityAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;

    public IdentityAuthenticationToken(String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

}
