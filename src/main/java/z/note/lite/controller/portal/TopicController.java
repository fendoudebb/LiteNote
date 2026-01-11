package z.note.lite.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import z.note.lite.config.context.WebsiteData;
import z.note.lite.controller.Endpoint;
import z.note.lite.entity.Post;
import z.note.lite.entity.TopicPostMonthlyStats;
import z.note.lite.service.portal.TopicService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class TopicController {

    @Resource
    private TopicService topicService;

    @Resource
    private WebsiteData websiteData;

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
        model.addAttribute("topic", topic);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sumPage", sumPage);
        model.addAttribute("posts", posts);
        if (!CollectionUtils.isEmpty(posts) &&
                !CollectionUtils.isEmpty(websiteData.getTopicPostMonthlyStatsList())) {
            List<TopicPostMonthlyStats> list = websiteData.getTopicPostMonthlyStatsList().stream()
                    .filter(item -> Objects.equals(item.getTopic(), topic))
                    .collect(Collectors.toList());
            model.addAttribute("topicPostMonthlyStatsList", list);
        }
        return "portal/topic";
    }

}
