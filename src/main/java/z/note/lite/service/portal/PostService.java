package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.repository.portal.PostRepository;
import z.note.lite.web.model.common.Post;

import java.util.List;

@Service
public class PostService {

    @Resource
    private PostRepository postRepository;

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

}
