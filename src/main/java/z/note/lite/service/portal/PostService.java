package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import z.note.lite.web.model.common.Post;
import z.note.lite.repository.portal.PostRepository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    @Resource
    private PostRepository postRepository;

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

    public List<Post> getOnlinePosts(int page, int size) {
        int offset = (page - 1) * size;
        return postRepository.getOnlinePosts(offset, size);
    }

    public Post getPost(Integer postId) {
        return postRepository.getPost(postId);
    }

    public List<Post> getRandomPosts() {
        return postRepository.getRandomPosts();
    }

    public int countOnlinePost() {
        return postRepository.countPostByStatusEquals(0);
    }

    public long count() {
        return postRepository.count();
    }

    public List<Post> getRankPosts() {
        return postRepository.getRankPosts();
    }

    public List<Post> getTodayOnHistoryPosts() {
        return postRepository.getTodayOnHistoryPosts();
    }

    public String sitemap(String uri) {
        return postRepository.sitemap(uri);
    }

    public int increasePv(Integer postId) {
        return postRepository.increasePv(postId);
    }

    public List<Post> fulltextSearch(String tsconfig, String keywords, int page, int size) {
        int offset = (page - 1) * size;
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
                """.replace("regconfig", tsconfig);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("keywords", keywords)
                .addValue("offset", offset)
                .addValue("size", size);
//        BeanPropertyRowMapper<Post> rowMapper = new BeanPropertyRowMapper<>(Post.class);
//        BeanPropertyRowMapper<Post> rowMapper = BeanPropertyRowMapper.newInstance(Post.class);
//        DataClassRowMapper<Post> rowMapper = new DataClassRowMapper<>(Post.class);
        return namedParameterJdbcTemplate.query(sql, namedParameters, rowMapper);
    }

}
