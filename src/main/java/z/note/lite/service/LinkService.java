package z.note.lite.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.web.model.common.Link;
import z.note.lite.repository.portal.LinkRepository;

import java.util.List;

@Service
public class LinkService {

    @Resource
    private LinkRepository linkRepository;

    public List<Link> getLinks() {
        return linkRepository.getLinks();
    }

}
