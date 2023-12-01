package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.SearchMgmtService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.response.PageableRes;

@RestController
public class SearchMgmtController {

    @Resource
    private SearchMgmtService searchMgmtService;

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
