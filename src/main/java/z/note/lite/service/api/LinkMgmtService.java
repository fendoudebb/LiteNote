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
import z.note.lite.web.http.request.LinkCreateReq;
import z.note.lite.web.http.request.LinkModifyReq;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.common.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LinkMgmtService {

    @Resource
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PageableRes findAll(int page, int size) {
        List<Criteria> wheres = new ArrayList<>();
        Query query = Query.query(Criteria.from(wheres));
        long count = jdbcAggregateTemplate.count(query, Link.class);
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        Iterable<Link> links = jdbcAggregateTemplate.findAll(query.offset(entry.getValue()).limit(entry.getKey()).sort(Sort.by("sort").ascending()), Link.class);
        PageableRes res = new PageableRes();
        res.setList(links);
        res.setCount(count);
        return res;
    }

    public int create(LinkCreateReq req) {
        boolean exists = jdbcAggregateTemplate.exists(Query.query(Criteria.where("url").is(req.getUrl())), Link.class);
        if (exists) throw new IllegalArgumentException("Link Exists");
        String sql = "insert into link(website, url, webmaster, sort) values(:website, :url, :webmaster, COALESCE((select max(sort) from link), 0)+1)";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(req));
    }

    public int update(LinkModifyReq req) {
        Query query = Query.query(Criteria.where("url").is(req.getUrl()).and("id").not(req.getId()));
        boolean exists = jdbcAggregateTemplate.exists(query, Link.class);
        if (exists) throw new IllegalArgumentException("Link Exists");
        String sql = "update link set website=:website, url=:url, webmaster=:webmaster, sort=:sort, update_ts=current_timestamp where id=:id";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(req));
    }

    public int updateStatus(Integer id) {
        String sql = "update link set status=1-status, update_ts=current_timestamp where id=:id";
        return namedParameterJdbcTemplate.update(sql, Map.of("id", id));
    }

}
