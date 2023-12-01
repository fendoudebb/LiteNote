package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.PageViewMgmtService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.response.PageableRes;

@RestController
public class PageViewMgmtController {

    @Resource
    private PageViewMgmtService pageViewMgmtService;

    @GetMapping(Endpoint.Api.PAGE_VIEW) // /api/pv
    public PageableRes getPageViews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") Integer status,
            @RequestParam(required = false) String startTs,
            @RequestParam(required = false) String endTs
    ) {
        return pageViewMgmtService.findAll(page, size, status, startTs, endTs);
    }

}
