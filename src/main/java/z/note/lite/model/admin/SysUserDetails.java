package z.note.lite.model.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class SysUserDetails implements UserDetails {

    private final SysUser sysUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] permissions = sysUser.getPermissions().stream()
                .map(SysPermission::getValue)
                .toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(permissions);
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !sysUser.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !sysUser.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !sysUser.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return !sysUser.isDisabled();
    }
}
