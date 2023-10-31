package z.note.lite.web.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import z.note.lite.web.Endpoint;
import z.note.lite.web.model.common.Post;
import z.note.lite.service.TopicService;

import java.util.List;

@Controller
public class TopicController {

    @Resource
    private TopicService topicService;

    @GetMapping(Endpoint.Portal.TOPICS) // /topics.html
    public String topics() {
        return "portal/topics";
    }

    @GetMapping(Endpoint.Portal.TOPIC) // /topic/{topic}.html
    public String topic(@PathVariable String topic, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        int count = topicService.countPostsByTopic(topic);
        int sumPage = (count - 1) / size + 1;
        if (page < 1 || page > sumPage) page = 1;
        if (size < 1 || size > 20) size = 20;
        List<Post> posts = topicService.getPostsByTopic(topic, page, size);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sumPage", sumPage);
        model.addAttribute("posts", posts);
        return "portal/topic";
    }

}
