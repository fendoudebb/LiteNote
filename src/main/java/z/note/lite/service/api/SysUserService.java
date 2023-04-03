package z.note.lite.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import z.note.lite.dto.response.SysUserDto;
import z.note.lite.model.admin.SysUser;

public interface SysUserService {

    Page<SysUserDto> getUsers(Pageable pageable);

}
