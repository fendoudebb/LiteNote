package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.RecordPageView;
import z.note.lite.mapper.portal.RecordPageViewMapper;

@Service
public class PageViewService {

    @Resource
    RecordPageViewMapper pageViewMapper;

    public long count() {
        return pageViewMapper.count();
    }

    public int insert(RecordPageView pageView) {
        return pageViewMapper.insert(pageView);
    }

}

