package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import z.note.lite.infra.PageUtils;
import z.note.lite.response.PageableRes;
import z.note.lite.entity.RecordSearch;

import java.util.List;
import java.util.Map;

@Service
public class RecordSearchMgmtService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public PageableRes findAll(int page, int size, String startTs, String endTs) {
        String sql = """
                select search.*, ip.ip, ip.country || COALESCE(ip.region,'') || COALESCE(ip.city,'') || COALESCE(ip.isp,'') as ipAddress from record_search search
                left join ip_pool ip on search.ip_id = ip.id
                %s
                order by id desc
                limit %d offset %d
                """;
        String where = "";
        if (StringUtils.hasText(startTs) && StringUtils.hasText(endTs)) {
            where = "where search.create_ts between '%s' and '%s'".formatted(startTs, endTs);
        }
        String countSQL = """
                select count(*) from record_search search
                %s
                """;
        Integer count = jdbcTemplate.queryForObject(countSQL.formatted(where), Integer.class);
        PageableRes res = new PageableRes();
        if (count == null || count == 0) {
            return res;
        }
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        List<RecordSearch> searches = jdbcTemplate.query(sql.formatted(where, entry.getKey(), entry.getValue()), new BeanPropertyRowMapper<>(RecordSearch.class));
        res.setList(searches);
        res.setCount(count);
        return res;
    }

}
