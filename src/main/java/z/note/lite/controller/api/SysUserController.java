package z.note.lite.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.constant.mvc.Endpoint;
import z.note.lite.dto.response.SysUserDto;
import z.note.lite.service.api.SysUserService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping(value = Endpoint.Api.USERS)
    public Page<SysUserDto> users(Pageable pageable) {
        return sysUserService.getUsers(pageable);
    }

}
