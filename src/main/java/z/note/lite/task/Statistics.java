package z.note.lite.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import z.note.lite.web.context.WebsiteData;
import z.note.lite.web.model.common.Link;
import z.note.lite.web.model.common.Post;
import z.note.lite.web.model.common.SearchRank;
import z.note.lite.web.model.common.Topic;
import z.note.lite.web.model.common.TopicData;
import z.note.lite.service.IpService;
import z.note.lite.service.LinkService;
import z.note.lite.service.PageViewService;
import z.note.lite.service.portal.PostService;
import z.note.lite.service.SearchService;
import z.note.lite.service.TopicService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Statistics {

    @Resource
    private WebsiteData websiteStatistics;

    @Resource
    private PostService postService;

    @Resource
    private IpService ipService;

    @Resource
    private PageViewService pageViewService;

    @Resource
    private LinkService linkService;

    @Resource
    private TopicService topicService;

    @Resource
    private SearchService searchService;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void countOnlinePost() {
        int onlinePosts = postService.countOnlinePost();
        if (onlinePosts == 0) {
            log.warn("online post is 0");
            return;
        }
        websiteStatistics.setOnlinePosts(onlinePosts);
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void randomPosts() {
        List<Post> randomPosts = postService.getRandomPosts();
        if (CollectionUtils.isEmpty(randomPosts)) {
            log.warn("random posts is empty");
            return;
        }
        websiteStatistics.setRandomPosts(randomPosts);
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    public void countPageView() {
        long pageView = pageViewService.countPageView();
        if (pageView == 0) {
            log.warn("page view is 0");
            return;
        }
        websiteStatistics.setPageView(pageView);
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void countIp() {
        int ipCount = ipService.countIp();
        if (ipCount == 0) {
            log.warn("ip count is 0");
            return;
        }
        websiteStatistics.setIpCount(ipCount);
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void links() {
        List<Link> links = linkService.getLinks();
        if (CollectionUtils.isEmpty(links)) {
            log.warn("links is empty");
            return;
        }
        websiteStatistics.setLinks(links);
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void rankPosts() {
        List<Post> rankPosts = postService.getRankPosts();
        if (CollectionUtils.isEmpty(rankPosts)) {
            log.warn("rank posts is empty");
            return;
        }
        websiteStatistics.setRankPosts(rankPosts);
    }

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.HOURS)
    public void rankSearches() {
        List<SearchRank> rankSearches = searchService.getRankSearches();
        if (CollectionUtils.isEmpty(rankSearches)) {
            log.warn("rank searches is empty");
            return;
        }
        websiteStatistics.setRankSearches(rankSearches);
    }

    @Scheduled(fixedRate = 6, timeUnit = TimeUnit.HOURS)
    public void topics() {
        List<TopicData> topicDataList = topicService.getTopicDataList();
        if (CollectionUtils.isEmpty(topicDataList)) {
            log.warn("topic data list is empty");
            return;
        }
        websiteStatistics.setTopicDataList(topicDataList);
    }

    @Scheduled(fixedRate = 6, timeUnit = TimeUnit.HOURS)
    public void recommendTopics() {
        List<Topic> recommendTopics = topicService.getRecommendTopics();
        if (CollectionUtils.isEmpty(recommendTopics)) {
            log.warn("recommend topics is empty");
            return;
        }
        websiteStatistics.setRecommendedTopics(recommendTopics);
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void todayOnHistory() {
        List<Post> posts = postService.getTodayOnHistoryPosts();
        if (CollectionUtils.isEmpty(posts)) {
            log.warn("today on history is empty");
            return;
        }
        websiteStatistics.setTodayOnHistoryPosts(posts);
    }

}