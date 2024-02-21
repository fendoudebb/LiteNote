package z.note.lite.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import z.note.lite.service.common.FullTextSearchService;
import z.note.lite.controller.Endpoint;
import z.note.lite.entity.Post;

import java.util.List;

@Controller
public class SearchController {

    @Resource
    private FullTextSearchService fullTextSearchService;

    @GetMapping(Endpoint.Portal.SEARCH) // /search.html
    public String search(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keywords, Model model) {
        if (StringUtils.hasText(keywords)) {
            List<Post> posts = fullTextSearchService.fulltextSearch(keywords, page);
            model.addAttribute("posts", posts);
        }
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "portal/search";
    }

}
