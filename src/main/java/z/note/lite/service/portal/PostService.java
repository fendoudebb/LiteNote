package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.Post;
import z.note.lite.entity.PostMonthlyStats;
import z.note.lite.entity.PostYearlyStats;
import z.note.lite.entity.TopicPostMonthlyStats;
import z.note.lite.mapper.portal.PostMapper;

import java.util.List;

@Service
public class PostService {

    @Resource
    PostMapper postMapper;

    public List<Post> getOnlinePosts(int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.getOnlinePosts(offset, size);
    }

    public Post getPost(Integer postId) {
        return postMapper.getPost(postId);
    }

    public List<Post> getRandomPosts() {
        return postMapper.getRandomPosts();
    }

    public int countOnlinePost() {
        return postMapper.countByStatus(0);
    }

//    public long count() {
//        return postMapper.count();
//    }

    public List<Post> getRankPosts() {
        return postMapper.getRankPosts();
    }

    public List<Post> getTodayInHistoryPosts() {
        return postMapper.getTodayInHistoryPosts();
    }

    public List<PostYearlyStats> getPostYearlyStatsList() {
        return postMapper.getPostYearlyStatsList();
    }

    public List<PostMonthlyStats> getPostMonthStatsList() {
        return postMapper.getPostMonthlyStatsList();
    }

    public List<TopicPostMonthlyStats> getTopicPostMonthStatsList() {
        return postMapper.getTopicPostMonthStatsList();
    }

    public String sitemap(String uri) {
        return postMapper.sitemap(uri);
    }

    public void increasePv(Integer postId) {
        postMapper.increasePv(postId);
    }

}
