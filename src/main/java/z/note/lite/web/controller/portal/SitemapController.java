package z.note.lite.web.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.web.Endpoint;
import z.note.lite.service.portal.PostService;
import z.note.lite.infra.RequestUtils;

@RestController
public class SitemapController {

    @Resource
    private PostService postService;

    @GetMapping(value = Endpoint.Portal.SITEMAP, produces = MediaType.APPLICATION_XML_VALUE) // /sitemap.xml
    public String sitemap() {
        String uri = RequestUtils.getScheme() + "://" + RequestUtils.getHost();
        return postService.sitemap(uri);
    }

}
