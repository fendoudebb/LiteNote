package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import z.note.lite.infra.PageUtils;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.common.Ip;
import z.note.lite.web.model.common.IpUnknown;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IpMgmtService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    public PageableRes findAll(int page, int size, String ip) {
        String sql = """
                select id, ip::text, country, region, city, isp, country_id, region_id, city_id, isp_id, create_ts
                from ip_pool
                %s
                order by id desc
                limit %d offset %d
                """;
        String where = "";
        if (StringUtils.hasText(ip)) {
            where = "where ip='%s'::inet".formatted(ip);
        }
        String countSQL = """
                select count(*) from ip_pool
                %s
                """;
        Integer count = jdbcTemplate.queryForObject(countSQL.formatted(where), Integer.class);
        PageableRes res = new PageableRes();
        if (count == null || count == 0) {
            return res;
        }
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        List<Ip> ips = jdbcTemplate.query(sql.formatted(where, entry.getKey(), entry.getValue()), new BeanPropertyRowMapper<>(Ip.class));
        res.setList(ips);
        res.setCount(count);
        return res;
    }

    public PageableRes findAllUnknown(int page, int size) {
        List<Criteria> wheres = new ArrayList<>();
        Query query = Query.query(Criteria.from(wheres));
        long count = jdbcAggregateTemplate.count(query, IpUnknown.class);
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        Iterable<IpUnknown> ipUnknowns = jdbcAggregateTemplate.findAll(query.offset(entry.getValue()).limit(entry.getKey()), IpUnknown.class);
        PageableRes postsDto = new PageableRes();
        postsDto.setList(ipUnknowns);
        postsDto.setCount(count);
        return postsDto;
    }

}
