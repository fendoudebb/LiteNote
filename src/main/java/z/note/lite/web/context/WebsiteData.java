package z.note.lite.web.context;

import lombok.Data;
import org.springframework.stereotype.Component;
import z.note.lite.web.model.common.Link;
import z.note.lite.web.model.common.Post;
import z.note.lite.web.model.common.SearchRank;
import z.note.lite.web.model.common.Topic;
import z.note.lite.web.model.common.TopicData;

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

    private List<SearchRank> rankSearches;

    private List<Topic> recommendedTopics;

    private List<Post> todayOnHistoryPosts;

}
