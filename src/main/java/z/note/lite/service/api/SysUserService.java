package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import z.note.lite.infra.PageUtils;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.admin.SysUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysUserService {

    @Resource
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PageableRes findAll(int page, int size) {
        List<Criteria> wheres = new ArrayList<>();
        Query query = Query.query(Criteria.from(wheres));
        long count = jdbcAggregateTemplate.count(query, SysUser.class);
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        Iterable<SysUser> users = jdbcAggregateTemplate.findAll(query.offset(entry.getValue()).limit(entry.getKey()).sort(Sort.by("id").ascending()), SysUser.class);
        PageableRes res = new PageableRes();
        res.setList(users);
        res.setCount(count);
        return res;
    }

}
