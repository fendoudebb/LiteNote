package z.note.lite.service.api;

import z.note.lite.model.admin.SysUser;
import z.note.lite.model.admin.SysUserDetails;
import z.note.lite.repository.api.SysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;

@Slf4j
@RequiredArgsConstructor
public class SysUserDetailService implements UserDetailsService {

    private final SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByUsername(username);
        if (sysUser == null) {
            log.error("User: {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }
        sysUser.setPermissions(new HashSet<>());
        return new SysUserDetails(sysUser);
    }
}
