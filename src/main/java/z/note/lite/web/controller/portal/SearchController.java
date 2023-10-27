package z.note.lite.web.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import z.note.lite.preferences.Portal;
import z.note.lite.preferences.portal.Search;
import z.note.lite.web.Endpoint;
import z.note.lite.web.model.common.Post;
import z.note.lite.service.portal.PostService;

import java.util.List;

@Controller
public class SearchController {

    @Resource
    private PostService postService;

    @Resource
    private Portal portal;

    @GetMapping(Endpoint.Portal.SEARCH) // /search.html
    public String search(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keywords, Model model) {
        if (page < 1) page = 1;
        Search search = portal.getSearch();
        if (StringUtils.hasText(keywords)) {
            if (keywords.length() > search.getMaxlength()) keywords = keywords.substring(0, search.getMaxlength());
            List<Post> posts = postService.fulltextSearch(search.getTsconfig(), keywords, page, search.getSize());
            model.addAttribute("posts", posts);
        }
        model.addAttribute("page", page);
        model.addAttribute("size", search.getSize());
        model.addAttribute("keywords", keywords);
        model.addAttribute("maxlength", search.getMaxlength());
        return "portal/search";
    }

}
