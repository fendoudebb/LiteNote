package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import z.note.lite.config.mybatis.ListTypeHandler;
import z.note.lite.entity.Post;
import z.note.lite.entity.TopicData;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("select id, title, topics, substring(description, 0, 100) as description, pv, create_ts from post where status = 0 order by id desc offset #{offset} rows fetch first #{size} rows only")
    @Result(column = "topics", property = "topics", typeHandler = ListTypeHandler.class)
    List<Post> getOnlinePosts(int offset, int size);

    @Select("select id, title, substring(description, 0, 100) as description, topics, content_html, word_count, status, pv, like_count, comment_count, comment_status, create_ts from post where id = #{postId} limit 1")
    @Result(column = "topics", property = "topics", typeHandler = ListTypeHandler.class)
    Post getPost(Integer postId);

    @Select("select id, title, pv from post where status=0 order by random() limit 10")
    List<Post> getRandomPosts();

    @Select("select count(*) from post")
    int count();

    @Select("select count(*) from post where status = #{status}")
    int countByStatus(int status);

    @Update("update post set pv=pv+1 where id=#{postId}")
    int increasePv(Integer postId);

    @Select("select id, title, pv from post where status=0 order by pv desc, id desc limit 5")
    List<Post> getRankPosts();

    @Select("select id, title, create_ts, pv from post where to_char(create_ts, 'MM-dd') = to_char(current_date, 'MM-dd') and status=0 order by pv desc, id desc")
    List<Post> getTodayOnHistoryPosts();

    @Select("select count(1) as count, unnest(topics) as name from post group by name order by count desc")
    List<TopicData> getTopicDataList();

    @Select("select id, title, topics, substring(description, 0, 100) as description, pv, create_ts from post where status=0 and #{topic}=ANY(topics) order by id desc offset #{offset} rows fetch first #{size} rows only")
    @Result(column = "topics", property = "topics", typeHandler = ListTypeHandler.class)
    List<Post> getPostsByTopic(String topic, int offset, int size);

    @Select("select count(*) as count from post where status=0 and #{topic}=ANY(topics)")
    int countPostByTopic(String topic);

    @Select("""
            select
            xmlroot(
            	xmlelement(name urlset, xmlattributes('https://www.sitemaps.org/schemas/sitemap/0.9' as xmlns),
            		xmlconcat(
            			xmlelement(name url,
            				xmlelement(name loc, #{uri}),
            				xmlelement(name lastmod, current_date),
            				xmlelement(name changefreq, 'always'),
            				xmlelement(name priority, 1)
            			),
            			xmlagg(
            				xmlelement(name url,
            					xmlforest(concat(#{uri}, '/p/', id, '.html') as loc),
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
    String sitemap(String uri);

}
