package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import z.note.lite.entity.Topic;
import z.note.lite.mapper.api.TopicMgmtMapper;
import z.note.lite.request.TopicCreateReq;
import z.note.lite.request.TopicModifyReq;
import z.note.lite.response.PageableRes;

import javax.management.Query;
import java.util.List;

@Service
public class TopicMgmtService {

    @Resource
    TopicMgmtMapper topicMgmtMapper;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PageableRes findAll(int page, int size, String topic) {
        int offset = (page - 1) * size;
        List<Topic> list = topicMgmtMapper.findAll(size, offset, topic);
        long count = topicMgmtMapper.count(topic);
        PageableRes res = new PageableRes();
        res.setList(list);
        res.setCount(count);
        return res;
    }

    public int createTopic(TopicCreateReq req) {
        Topic topic = topicMgmtMapper.findByName(req.getName());
        if (topic != null) throw new IllegalArgumentException("Topic Exists");
        return topicMgmtMapper.insert(req.getName());
    }

    public int updateTopic(TopicModifyReq req) {
        Topic topic = topicMgmtMapper.findByName(req.getName());
        if (topic != null && !topic.getId().equals(req.getId())) throw new IllegalArgumentException("Topic Exists");
        topic = new Topic();
        BeanUtils.copyProperties(req, topic);
        return topicMgmtMapper.update(topic);
    }

}
