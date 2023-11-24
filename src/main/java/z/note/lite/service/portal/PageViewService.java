package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.repository.portal.PageViewRepository;
import z.note.lite.web.model.common.PageView;

@Service
public class PageViewService {

    @Resource
    private PageViewRepository pageViewRepository;

    public long countPageView() {
        return pageViewRepository.countPageView();
    }

    public PageView save(PageView pageView) {
        return pageViewRepository.save(pageView);
    }

}

