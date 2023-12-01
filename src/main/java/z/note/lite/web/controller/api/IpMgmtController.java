package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.IpMgmtService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.response.PageableRes;

@RestController
public class IpMgmtController {

    @Resource
    private IpMgmtService ipMgmtService;

    @GetMapping(Endpoint.Api.IPS) // /api/ips
    public PageableRes getIps(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String ip
    ) {
        return ipMgmtService.findAll(page, size, ip);
    }

    @GetMapping(Endpoint.Api.IPS_UNKNOWN) // /api/ips/unknown
    public PageableRes getIpsUnknowns(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ipMgmtService.findAllUnknown(page, size);
    }

}
