package z.note.lite.config.context;

import lombok.Data;
import org.springframework.stereotype.Component;
import z.note.lite.entity.Link;
import z.note.lite.entity.Post;
import z.note.lite.entity.TopicViewBoxplotStats;
import z.note.lite.entity.PostDailyStats;
import z.note.lite.entity.PostHourlyStats;
import z.note.lite.entity.PostMonthlyStats;
import z.note.lite.entity.PostProgressStats;
import z.note.lite.entity.PostYearlyStats;
import z.note.lite.entity.RecordSearchRank;
import z.note.lite.entity.Topic;
import z.note.lite.entity.TopicData;
import z.note.lite.entity.TopicPostMonthlyStats;

import java.util.List;

@Data
@Component
public class WebsiteData {

    private int onlinePosts;

    private int ipCount;

    private long pageView;

    private List<Link> links;

    private List<Post> rankPosts;

    private List<Post> randomPosts;

    private List<TopicData> topicDataList;

    private List<RecordSearchRank> rankSearches;

    private List<Topic> recommendedTopics;

    private List<Post> todayInHistoryPosts;

    private List<PostYearlyStats> postYearlyStatsList;

    private List<PostMonthlyStats> postMonthlyStatsList;

    private List<PostDailyStats> postDailyStatsList;

    private List<PostHourlyStats> postHourlyStatsList;

    private PostProgressStats postProgressStats;

    private List<TopicPostMonthlyStats> topicPostMonthlyStatsList;

    private List<TopicViewBoxplotStats> topicViewBoxplotStats;

}
