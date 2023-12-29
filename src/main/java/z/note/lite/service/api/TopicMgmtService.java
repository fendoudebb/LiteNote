package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import z.note.lite.infra.PageUtils;
import z.note.lite.web.http.request.TopicCreateReq;
import z.note.lite.web.http.request.TopicModifyReq;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.common.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TopicMgmtService {

    @Resource
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PageableRes findAll(int page, int size, String topic) {
        List<Criteria> wheres = new ArrayList<>();
        if (Objects.nonNull(topic)) {
            wheres.add(Criteria.where("name").is(topic));
        }
        Query query = Query.query(Criteria.from(wheres));
        long count = jdbcAggregateTemplate.count(query, Topic.class);
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        Iterable<Topic> topics = jdbcAggregateTemplate.findAll(query.offset(entry.getValue()).limit(entry.getKey()).sort(Sort.by("id").descending()), Topic.class);
        PageableRes res = new PageableRes();
        res.setList(topics);
        res.setCount(count);
        return res;
    }

    public int createTopic(TopicCreateReq req) {
        boolean exists = jdbcAggregateTemplate.exists(Query.query(Criteria.where("name").is(req.getName())), Topic.class);
        if (exists) throw new IllegalArgumentException("Topic Exists");
        String sql = "insert into topic(name, sort) values(:name, COALESCE((select max(sort) from topic), 0)+1)";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(req));
    }

    public int updateTopic(TopicModifyReq req) {
        Query query = Query.query(Criteria.where("name").is(req.getName()).and("id").not(req.getId()));
        boolean exists = jdbcAggregateTemplate.exists(query, Topic.class);
        if (exists) throw new IllegalArgumentException("Topic Exists");
        String sql = "update topic set name=:name, sort=:sort, update_ts=current_timestamp where id=:id";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(req));
    }

}
