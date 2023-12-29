package z.note.lite.web.security.authentication.token;

import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.Setter;
import z.note.lite.web.model.admin.SysUser;

import java.util.Collection;

@Setter
@Getter
@ToString
public class SysUserAuthenticationToken extends AbstractAuthenticationToken {

    private final SysUser sysUser;

    public SysUserAuthenticationToken(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.sysUser = sysUser;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return sysUser;
    }

}
