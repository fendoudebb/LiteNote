package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.config.time.Formatter;
import z.note.lite.service.api.RecordPageViewMgmtService;
import z.note.lite.controller.Endpoint;
import z.note.lite.response.PageableRes;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Validated
@RestController
public class PageViewMgmtController {

    @Resource
    private RecordPageViewMgmtService pageViewMgmtService;

    @GetMapping(Endpoint.Api.PAGE_VIEW) // /api/pv
    public PageableRes getPageViews(
            @Min (1) @RequestParam(defaultValue = "1") int page,
            @Min(1) @Max (10) @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") Integer status,
            @RequestParam(required = false) String startTs,
            @RequestParam(required = false) String endTs
    ) {
        OffsetDateTime start = null;
        if (StringUtils.hasText(startTs)) {
            start = OffsetDateTime.of(LocalDateTime.parse(startTs, Formatter.DATE_TIME), OffsetDateTime.now().getOffset());
        }
        OffsetDateTime end = null;
        if (StringUtils.hasText(endTs)) {
            end = OffsetDateTime.of(LocalDateTime.parse(endTs, Formatter.DATE_TIME), OffsetDateTime.now().getOffset());
        }
        return pageViewMgmtService.findAll(page, size, status, start, end);
    }

}
