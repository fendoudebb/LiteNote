package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.api.PostMgmtService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.request.PostReq;
import z.note.lite.web.http.request.PostStatusReq;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.common.Post;

import java.time.LocalDateTime;

@RestController
public class PostMgmtController {

    @Resource
    private PostMgmtService postMgmtService;

    @GetMapping(Endpoint.Api.POSTS) // /api/posts
    public PageableRes getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime startTs,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime endTs
    ) {
        return postMgmtService.findAll(page, size, id, status, startTs, endTs);
    }

    @GetMapping(Endpoint.Api.POST_ID) // /api/post/{postId}
    public Post getPost(@PathVariable Integer postId) {
        return postMgmtService.findById(postId);
    }

    @PostMapping(Endpoint.Api.POST) // /api/post
    public int create(@Validated @RequestBody PostReq post, Authentication authentication) {
        return postMgmtService.createPost(post, authentication);
    }

    @PutMapping(Endpoint.Api.POST) // /api/post
    public int update(@Validated @RequestBody PostReq post) {
        return postMgmtService.updatePost(post);
    }

    @PutMapping(Endpoint.Api.POST_STATUS) // /api/post/status
    public int updateStatus(@RequestBody PostStatusReq req) {
        return postMgmtService.updateStatus(req.getPostId(), req.getStatus());
    }

    @PutMapping(Endpoint.Api.POST_COMMENT_STATUS) // /api/post/comment/status
    public int postCommentStatus(@PathVariable Integer postId) {
        return postMgmtService.toggleCommentStatus(postId);
    }

}
