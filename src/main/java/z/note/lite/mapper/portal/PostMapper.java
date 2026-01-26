package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import z.note.lite.config.mybatis.ListTypeHandler;
import z.note.lite.entity.Post;
import z.note.lite.entity.PostDailyStats;
import z.note.lite.entity.PostHourlyStats;
import z.note.lite.entity.PostMonthlyStats;
import z.note.lite.entity.PostProgressStats;
import z.note.lite.entity.PostYearlyStats;
import z.note.lite.entity.TopicData;
import z.note.lite.entity.TopicPostMonthlyStats;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("select id, title, topics, substring(description, 0, 100) as description, pv, create_ts from post where status = 0 order by id desc offset #{offset} rows fetch first #{size} rows only")
    @Result(column = "topics", property = "topics", typeHandler = ListTypeHandler.class)
    List<Post> getOnlinePosts(int offset, int size);

    @Select("""
            select * from (
                select id, title, substring(description, 0, 100) as description, topics, content_html, word_count, status, pv, comment_status, create_ts,
                lead(id) over (order by id desc)    as prev_id,
                lead(title) over (order by id desc) as prev_title,
                lag(id) over (order by id desc)     as next_id,
                lag(title) over (order by id desc)  as next_title
                from post where status=0) tmp
            where tmp.id = #{postId}
            """)
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

    @Select("select id, title, create_ts, pv from post where to_char(create_ts, 'MM-dd') = to_char(current_date, 'MM-dd') and status=0 order by id desc")
    List<Post> getTodayInHistoryPosts();

    @Select("select count(1) as count, unnest(topics) as name from post where status=0 group by name order by count desc")
    List<TopicData> getTopicDataList();

    @Select("select id, title, topics, substring(description, 0, 100) as description, pv, create_ts from post where status=0 and topics @> ARRAY[#{topic}::text] order by id desc offset #{offset} rows fetch first #{size} rows only")
    @Result(column = "topics", property = "topics", typeHandler = ListTypeHandler.class)
    List<Post> getPostsByTopic(String topic, int offset, int size);

    @Select("select count(*) as count from post where status=0 and topics @> ARRAY[#{topic}::text]")
    int countPostByTopic(String topic);

    @Select("""
            select extract(year from create_ts) as year, count(*) as count from post
            where status=0
            group by year
            order by year
            """)
    List<PostYearlyStats> getPostYearlyStatsList();

    @Select("""
            select extract(year from create_ts) as year, extract(month from create_ts) as month, count(*) as count from post
            where status=0
            group by year, month
            order by year, month
            """)
    List<PostMonthlyStats> getPostMonthlyStatsList();

    @Select("""
            select extract(year from create_ts) as year,
                   extract(month from create_ts) as month,
                   extract(day from create_ts) as day,
                   count(*) as count
            from post
            where status=0
            group by 1, 2, 3
            order by 1, 2, 3
            """)
    List<PostDailyStats> getPostDailyStatsList();

    @Select("""
            select extract(hour from create_ts) as hour,
                   count(*) as count
            from post
            where status=0
            group by 1
            order by 1
            """)
    List<PostHourlyStats> getPostHourlyStatsList();

    @Select("""
            select count(1) as year_count,
                   count(1) filter ( where date_trunc('month', create_ts) =  date_trunc('month', current_timestamp)) as month_count,
                   count(1) filter ( where date_trunc('day', create_ts) =  date_trunc('day', current_timestamp)) as day_count
            from post
            where status=0 and create_ts >= date_trunc('year', current_timestamp);
            """)
    PostProgressStats getPostProgressStats();

    @Select("""
            WITH topic_monthly AS (
              SELECT
                extract(year from create_ts) as year,
                extract(month from create_ts) as month,
                unnest(topics) AS topic,
                COUNT(id) AS month_count
              FROM post
              WHERE status = 0
              GROUP BY year, month, topic
            )
            SELECT
              topic,
              year,
              month,
              month_count,
              SUM(month_count) OVER(
                PARTITION BY topic
                ORDER BY year, month
              ) AS cumulative_count
            FROM topic_monthly
            ORDER BY topic, year, month
            """)
    List<TopicPostMonthlyStats> getTopicPostMonthStatsList();

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
