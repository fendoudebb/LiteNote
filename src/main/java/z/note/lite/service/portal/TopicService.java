package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.Post;
import z.note.lite.entity.Topic;
import z.note.lite.mapper.portal.PostMapper;
import z.note.lite.mapper.portal.TopicMapper;
import z.note.lite.entity.TopicData;

import java.util.List;

@Service
public class TopicService {

    @Resource
    PostMapper postMapper;

    @Resource
    TopicMapper topicMapper;

    public List<TopicData> getTopicDataList() {
        return postMapper.getTopicDataList();
    }

    public List<Topic> getRecommendTopics() {
        return topicMapper.getRecommendTopics();
    }

    public List<Post> getPostsByTopic(String topic, int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.getPostsByTopic(topic, offset, size);
    }

    public int countPostsByTopic(String topic) {
        return postMapper.countPostByTopic(topic);
    }

}
