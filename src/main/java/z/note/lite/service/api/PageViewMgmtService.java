package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import z.note.lite.infra.PageUtils;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.common.PageView;

import java.util.List;
import java.util.Map;

@Service
public class PageViewMgmtService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public PageableRes findAll(int page, int size, Integer status, String startTs, String endTs) {
        String sql = """
                select pv.*, ip.ip, ip.country || COALESCE(ip.region,'') || COALESCE(ip.city,'') || COALESCE(ip.isp,'') as ipAddress from %s pv
                left join ip_pool ip on pv.ip_id = ip.id
                %s
                order by id desc
                limit %d offset %d
                """;
        String table;
        String where = "";
        if (status == 0) {
            table = "record_page_view";
        } else {
            table = "record_invalid_request";
        }
        if (StringUtils.hasText(startTs) && StringUtils.hasText(endTs)) {
            where = "where pv.create_ts between '%s' and '%s'".formatted(startTs, endTs);
        }
        String countSQL = """
                select count(*) from %s pv
                %s
                """;
        Integer count = jdbcTemplate.queryForObject(countSQL.formatted(table, where), Integer.class);
        PageableRes res = new PageableRes();
        if (count == null || count == 0) {
            return res;
        }
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        List<PageView> pageViews = jdbcTemplate.query(sql.formatted(table, where, entry.getKey(), entry.getValue()), new BeanPropertyRowMapper<>(PageView.class));
        res.setList(pageViews);
        res.setCount(count);
        return res;
    }

}
