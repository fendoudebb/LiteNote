package z.note.lite.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import z.note.lite.web.http.response.SysUserRes;

public interface SysUserService {

    Page<SysUserRes> getUsers(Pageable pageable);

}
