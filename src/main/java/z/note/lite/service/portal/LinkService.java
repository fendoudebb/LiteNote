package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.Link;
import z.note.lite.mapper.portal.LinkMapper;

import java.util.List;

@Service
public class LinkService {

    @Resource
    LinkMapper linkMapper;

    public List<Link> list() {
        return linkMapper.list();
    }

}
