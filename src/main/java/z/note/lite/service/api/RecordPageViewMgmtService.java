package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.RecordPageView;
import z.note.lite.mapper.api.RecordPageViewMgmtMapper;
import z.note.lite.response.PageableRes;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class RecordPageViewMgmtService {

    @Resource
    RecordPageViewMgmtMapper recordPageViewMgmtMapper;

    public PageableRes findAll(int page, int size, Integer status, OffsetDateTime startTs, OffsetDateTime endTs) {
        List<RecordPageView> list = recordPageViewMgmtMapper.findAll(size, (page - 1) * size, status, startTs, endTs);
        long count = recordPageViewMgmtMapper.count(status, startTs, endTs);
        PageableRes res = new PageableRes();
        res.setList(list);
        res.setCount(count);
        return res;
    }

}
