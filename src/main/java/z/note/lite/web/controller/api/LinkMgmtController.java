package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.LinkMgmtService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.request.LinkCreateReq;
import z.note.lite.web.http.request.LinkModifyReq;
import z.note.lite.web.http.response.PageableRes;

@RestController
public class LinkMgmtController {

    @Resource
    private LinkMgmtService linkMgmtService;

    @GetMapping(Endpoint.Api.LINKS) // /api/links
    public PageableRes getList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return linkMgmtService.findAll(page, size);
    }

    @PostMapping(Endpoint.Api.LINK) // /api/link
    public int create(@Validated @RequestBody LinkCreateReq req) {
        return linkMgmtService.create(req);
    }

    @PutMapping(Endpoint.Api.LINK) // /api/link
    public int update(@Validated @RequestBody LinkModifyReq req) {
        return linkMgmtService.update(req);
    }

    @PutMapping(Endpoint.Api.LINK_STATUS) // /api/link/status/{id}
    public int updateStatus(@PathVariable Integer id) {
        return linkMgmtService.updateStatus(id);
    }

}
