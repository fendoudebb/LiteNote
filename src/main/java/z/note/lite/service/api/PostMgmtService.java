package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import z.note.lite.infra.PageUtils;
import z.note.lite.repository.api.PostMgmtRepository;
import z.note.lite.web.http.request.PostReq;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.http.response.PostMgmtRes;
import z.note.lite.web.model.admin.SysUser;
import z.note.lite.web.model.common.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PostMgmtService {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Resource
    private PostMgmtRepository postMgmtRepository;

    public Post findById(Integer postId) {
        return jdbcAggregateTemplate.findById(postId, Post.class);
    }

    public int createPost(PostReq req, Authentication authentication) {
        req.setUid(((SysUser) authentication.getPrincipal()).getId());
        String sql = """
                insert into post(
                    id, uid, title, description, topics, content, content_html, word_count, status, prop, comment_status, images
                ) values(
                    (select max(id) from post) + 1, :uid, :title, :description, string_to_array(:topics, ','), :content, :contentHtml, :wordCount, :status, :prop, :commentStatus, string_to_array(:images, ',')
                )
                """;
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(req));
    }

    public int updatePost(PostReq req) {
        String sql = """
                update post set
                    title=:title,
                    description=:description,
                    topics=string_to_array(:topics, ','),
                    content=:content,
                    content_html=:contentHtml,
                    word_count=:wordCount,
                    status=:status,
                    prop=:prop,
                    comment_status=:commentStatus,
                    images=string_to_array(:images, ','),
                    update_ts=current_timestamp
                where id=:id
                """;
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(req));
    }

    public PageableRes findAll(int page, int size, Integer id, Integer status, LocalDateTime startTs, LocalDateTime endTs) {
        List<Criteria> wheres = new ArrayList<>();
        if (Objects.nonNull(id)) {
            wheres.add(Criteria.where("id").is(id));
        }
        if (Objects.nonNull(status)) {
            wheres.add(Criteria.where("status").is(status));
        }
        if (Objects.nonNull(startTs) && Objects.nonNull(endTs)) {
            wheres.add(Criteria.where("createTs").between(startTs, endTs));
        }
        Query query = Query.query(Criteria.from(wheres));
        long count = jdbcAggregateTemplate.count(query, Post.class);
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        Iterable<PostMgmtRes> posts = jdbcAggregateTemplate.findAll(query.offset(entry.getValue()).limit(entry.getKey()).sort(Sort.by("id").descending()), PostMgmtRes.class);
        PageableRes res = new PageableRes();
        res.setList(posts);
        res.setCount(count);
        return res;
    }

    public int updateStatus(Integer postId, Short status) {
        return postMgmtRepository.updateStatus(postId, status);
    }

    public int toggleCommentStatus(Integer postId) {
        return postMgmtRepository.toggleCommentStatus(postId);
    }

}
