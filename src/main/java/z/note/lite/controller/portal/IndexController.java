package z.note.lite.controller.portal;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import z.note.lite.controller.Endpoint;
import z.note.lite.config.context.WebsiteData;
import z.note.lite.entity.Post;
import z.note.lite.service.portal.PostService;

import java.util.List;

@Slf4j
@Controller
public class IndexController {

    @Resource
    private PostService postService;

    @Resource
    private WebsiteData websiteData;

    @GetMapping(Endpoint.Portal.INDEX) // /
    public String index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        if (size < 1 || size > 20) size = 20;
        int sumPage = (websiteData.getOnlinePosts() - 1) / size + 1;
        if (page < 1 || page > sumPage) page = 1;
        List<Post> posts = postService.getOnlinePosts(page, size);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sumPage", sumPage);
        model.addAttribute("posts", posts);
        return "portal/index";
    }

}
