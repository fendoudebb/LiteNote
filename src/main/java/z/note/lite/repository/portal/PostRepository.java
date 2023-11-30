package z.note.lite.repository.portal;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import z.note.lite.web.model.common.Post;
import z.note.lite.web.model.common.TopicData;

import java.util.List;

public interface PostRepository extends ListCrudRepository<Post, Integer> {

    @Query("select id, title, topics, substring(description, 0, 100) as description, pv, create_ts from post where status = 0 order by id desc offset :offset rows fetch first :size rows only")
    List<Post> getOnlinePosts(@Param("offset") int offset, @Param("size") int size);

    @Query("select id, title, substring(description, 0, 100) as description, topics, content_html, word_count, status, pv, like_count, comment_count, comment_status, create_ts from post where id = :postId limit 1")
    Post getPost(@Param("postId") Integer postId);

    @Query("select id, title, pv from post where status=0 order by random() limit 10")
    List<Post> getRandomPosts();

    int countPostByStatusEquals(@Param("status") int status);

    @Query("select count(1) as count, unnest(topics) as name from post group by name order by count desc")
    List<TopicData> getTopicDataList();

    @Query("select id, title, create_ts, pv from post where to_char(create_ts, 'MM-dd') = to_char(current_date, 'MM-dd') and status=0 order by pv desc, id desc")
    List<Post> getTodayOnHistoryPosts();

    @Query("select id, title, pv from post where status=0 order by pv desc, id desc limit 5")
    List<Post> getRankPosts();

    @Query("select id, title, topics, substring(description, 0, 100) as description, pv, create_ts from post where status=0 and :topic=ANY(topics) order by id desc offset :offset rows fetch first :size rows only")
    List<Post> getPostsByTopic(@Param("topic") String topic, @Param("offset") int offset, @Param("size") int size);

    @Query("select count(*) as count from post where status=0 and :topic=ANY(topics)")
    int countPostByTopic(@Param("topic") String topic);

    @Modifying
    @Query("update post set pv=pv+1 where id=:postId")
    int increasePv(@Param("postId") Integer postId);

    @Query("""
            select
            xmlroot(
            	xmlelement(name urlset, xmlattributes('https://www.sitemaps.org/schemas/sitemap/0.9' as xmlns),
            		xmlconcat(
            			xmlelement(name url,
            				xmlelement(name loc, :uri),
            				xmlelement(name lastmod, current_date),
            				xmlelement(name changefreq, 'always'),
            				xmlelement(name priority, 1)
            			),
            			xmlagg(
            				xmlelement(name url,
            					xmlforest(concat(:uri, '/p/', id, '.html') as loc),
            					xmlelement(name lastmod, current_date),
            					xmlelement(name changefreq, 'daily'),
            					xmlelement(name priority, 0.8)
            				)
            			order by id desc)
            		)
            	)
            	,version '1.0', standalone yes)::text as sitemap
            from post where status=0
            """)
    String sitemap(@Param("uri") String uri);

}
