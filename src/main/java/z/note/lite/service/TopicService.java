package z.note.lite.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.web.model.common.Post;
import z.note.lite.web.model.common.Topic;
import z.note.lite.web.model.common.TopicData;
import z.note.lite.repository.portal.PostRepository;
import z.note.lite.repository.portal.TopicRepository;

import java.util.List;

@Service
public class TopicService {

    @Resource
    private PostRepository postRepository;

    @Resource
    private TopicRepository topicRepository;

    public List<TopicData> getTopicDataList() {
        return postRepository.getTopicDataList();
    }

    public List<Topic> getRecommendTopics() {
        return topicRepository.getRecommendTopics();
    }

    public List<Post> getPostsByTopic(String topic, int page, int size) {
        int offset = (page - 1) * size;
        return postRepository.getPostsByTopic(topic, offset, size);
    }

    public int countPostsByTopic(String topic) {
        return postRepository.countPostByTopic(topic);
    }

}
