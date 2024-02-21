package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.TopicMgmtService;
import z.note.lite.controller.Endpoint;
import z.note.lite.request.TopicCreateReq;
import z.note.lite.request.TopicModifyReq;
import z.note.lite.response.PageableRes;

@RestController
public class TopicMgmtController {

    @Resource
    private TopicMgmtService topicMgmtService;

    @GetMapping(Endpoint.Api.TOPICS) // /api/topics
    public PageableRes getTopics(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String topic
    ) {
        return topicMgmtService.findAll(page, size, topic);
    }

    @PostMapping(Endpoint.Api.TOPIC) // /api/topic
    public int createTopic(@Validated @RequestBody TopicCreateReq req) {
        return topicMgmtService.createTopic(req);
    }

    @PutMapping(Endpoint.Api.TOPIC) // /api/topic
    public int updateTopic(@Validated @RequestBody TopicModifyReq req) {
        return topicMgmtService.updateTopic(req);
    }

}
