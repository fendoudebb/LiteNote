package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.RecordSearchMgmtService;
import z.note.lite.controller.Endpoint;
import z.note.lite.response.PageableRes;

@RestController
public class SearchMgmtController {

    @Resource
    private RecordSearchMgmtService searchMgmtService;

    @GetMapping(Endpoint.Api.SEARCHES) // /api/searches
    public PageableRes getSearches(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String startTs,
            @RequestParam(required = false) String endTs
    ) {
        return searchMgmtService.findAll(page, size, startTs, endTs);
    }

}
