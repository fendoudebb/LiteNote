package z.note.lite.service.mobile;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import z.note.lite.web.http.response.MobilePostDetailRes;
import z.note.lite.web.http.response.MobilePostRes;
import z.note.lite.web.http.response.MobilePostListRes;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MobilePostService {

    @Resource
    JdbcClient jdbcClient;

    private final BeanPropertyRowMapper<MobilePostRes> rowMapper = new BeanPropertyRowMapper<>(MobilePostRes.class) {
        @Override
        public MobilePostRes mapRow(ResultSet rs, int rowNumber) throws SQLException {
            MobilePostRes post = super.mapRow(rs, rowNumber);
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

    public MobilePostListRes getOnlinePosts(String topic, int page, int size) {
        String whereClause = StringUtils.hasText(topic) ? "and :topic=ANY(topics)" : "";
        String countSql = """
                select count(*) as count from post where status=0 %s;
                """.formatted(whereClause);
        Integer count = jdbcClient.sql(countSql)
                .params(StringUtils.hasText(topic) ? Map.of("topic", topic) : Map.of())
                .query(Integer.class)
                .single();
        if (size < 1 || size > 20) size = 20;
        int sumPage = (count - 1) / size + 1;
        if (page < 1 || page > sumPage) page = 1;
        int offset = (page - 1) * size;
        String sql = """
                select id as "postId", title, description, pv, topics as topicArray, trunc(EXTRACT(EPOCH FROM (create_ts)) * 1000) as "postTime" from post where status=0 %s order by id desc limit :limit offset :offset
                """.formatted(whereClause);
        List<MobilePostRes> posts = jdbcClient.sql(sql)
                .params(StringUtils.hasText(topic) ? Map.of("topic", topic) : Map.of())
                .param("limit", size)
                .param("offset", offset)
                .query(rowMapper)
                .list();
        return MobilePostListRes.builder()
                .currentPage(page)
                .totalPage(sumPage)
                .posts(posts)
                .build();
    }

    public MobilePostDetailRes getPost(Integer postId) {
        String sql = """
                select id as "postId", title, topics as "topicArray", content_html as "contentHtml", word_count as "postWordCount", pv, trunc(EXTRACT(EPOCH FROM (create_ts)) * 1000) as "postTime"
                from post where id=:postId and status=0
                """;
        MobilePostRes post = jdbcClient.sql(sql)
                .param("postId", postId)
                .query(rowMapper)
                .single();
        return MobilePostDetailRes.builder().post(post).build();
    }

}
