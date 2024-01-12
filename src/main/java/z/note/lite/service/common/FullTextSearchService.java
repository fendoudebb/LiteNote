package z.note.lite.service.common;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import z.note.lite.preferences.Portal;
import z.note.lite.preferences.portal.Search;
import z.note.lite.web.model.common.Post;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FullTextSearchService {

    @Resource
    Portal portal;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final BeanPropertyRowMapper<Post> rowMapper = new BeanPropertyRowMapper<>(Post.class) {
        @Override
        public Post mapRow(ResultSet rs, int rowNumber) throws SQLException {
            Post post = super.mapRow(rs, rowNumber);
            if (Objects.nonNull(post)) {
                Array topics = rs.getArray("topicArray");
                if (Objects.nonNull(topics)) {
                    String[] array = (String[]) topics.getArray();
                    post.setTopics(Arrays.asList(array));
                }
            }
            return post;
        }
    };

    public List<Post> fulltextSearch(String keywords, int page) {
        if (page < 1) page = 1;
        Search search = portal.getSearch();
        if (keywords.length() > search.getMaxlength()) keywords = keywords.substring(0, search.getMaxlength());
        int offset = (page - 1) * search.getSize();
        String sql = """
                select id, pv, create_ts,
                       ts_headline('regconfig', title, q) as title,
                       string_to_array(ts_headline('regconfig', array_to_string(topics, ','), q), ',') as topicArray,
                       ts_headline('regconfig', description, q, 'MaxFragments=0, MaxWords=10, MinWords=3, StartSel=<b>, StopSel=</b>') as description
                from (select id, title, description, topics, pv, create_ts,
                             tsvector_concat(setweight(to_tsvector('regconfig', title), 'A'), setweight(to_tsvector('regconfig', description), 'D')) v,
                             websearch_to_tsquery('regconfig', :keywords) q
                      from post where status=0
                ) temp
                where v @@ q order by ts_rank(v, q) desc offset :offset fetch first :size rows only
                """.replace("regconfig", search.getTsconfig());
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("keywords", keywords)
                .addValue("offset", offset)
                .addValue("size", search.getSize());
//        BeanPropertyRowMapper<Post> rowMapper = new BeanPropertyRowMapper<>(Post.class);
//        BeanPropertyRowMapper<Post> rowMapper = BeanPropertyRowMapper.newInstance(Post.class);
//        DataClassRowMapper<Post> rowMapper = new DataClassRowMapper<>(Post.class);
        return namedParameterJdbcTemplate.query(sql, namedParameters, rowMapper);
    }

}
