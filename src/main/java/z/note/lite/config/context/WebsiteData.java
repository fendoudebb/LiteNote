package z.note.lite.config.context;

import lombok.Data;
import org.springframework.stereotype.Component;
import z.note.lite.entity.Link;
import z.note.lite.entity.Post;
import z.note.lite.entity.RecordSearchRank;
import z.note.lite.entity.Topic;
import z.note.lite.entity.TopicData;

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

    private List<Post> todayOnHistoryPosts;

}
