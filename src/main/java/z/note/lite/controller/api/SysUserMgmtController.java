package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.SysUserService;
import z.note.lite.controller.Endpoint;
import z.note.lite.request.SysUserCreateReq;
import z.note.lite.request.SysUserModifyReq;
import z.note.lite.response.PageableRes;

@Slf4j
@RestController
public class SysUserMgmtController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping(value = Endpoint.Api.USERS) // /api/users
    public PageableRes getList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return sysUserService.findAll(page, size);
    }

    @PostMapping(Endpoint.Api.USER) // /api/user
    public int create(@Validated @RequestBody SysUserCreateReq req, Authentication authentication) {
        req.setCreateBy(authentication.getName());
        return sysUserService.create(req);
    }

    @PutMapping(Endpoint.Api.USER) // /api/user
    public int update(@Validated @RequestBody SysUserModifyReq req, Authentication authentication) {
        req.setUpdateBy(authentication.getName());
        return sysUserService.update(req);
    }

    @PutMapping(Endpoint.Api.USER_STATUS) // /api/user/status/{id}
    public int updateStatus(@PathVariable Long id) {
        return sysUserService.updateStatus(id);
    }

}
