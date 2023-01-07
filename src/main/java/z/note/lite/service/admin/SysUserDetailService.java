package z.note.lite.service.admin;

import z.note.lite.model.admin.SysUser;
import z.note.lite.model.admin.SysUserDetails;
import z.note.lite.repository.admin.SysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


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
        return new SysUserDetails(sysUser);
    }
}
