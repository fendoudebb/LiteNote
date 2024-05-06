package z.note.lite.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.portal.PostService;
import z.note.lite.infra.RequestUtils;

import static z.note.lite.controller.Endpoint.Portal.SITEMAP;
import static z.note.lite.controller.Endpoint.Portal.SITEMAP_GOOGLE;

@RestController
public class SitemapController {

    @Resource
    private PostService postService;

    @GetMapping(value = {SITEMAP, SITEMAP_GOOGLE}, produces = MediaType.APPLICATION_XML_VALUE) // /sitemap.xml
    public String sitemap() {
        String uri = RequestUtils.getScheme() + "://" + RequestUtils.getHost();
        return postService.sitemap(uri);
    }

}
