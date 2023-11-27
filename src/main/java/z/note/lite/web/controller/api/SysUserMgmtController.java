package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.http.response.SysUserRes;
import z.note.lite.service.api.SysUserService;

@Slf4j
@RestController
public class SysUserMgmtController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping(value = Endpoint.Api.USERS) // /api/users
    public PageableRes getList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return sysUserService.findAll(page, size);
    }

}