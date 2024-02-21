package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.config.time.Formatter;
import z.note.lite.service.api.PostMgmtService;
import z.note.lite.controller.Endpoint;
import z.note.lite.request.PostReq;
import z.note.lite.request.PostStatusReq;
import z.note.lite.response.PageableRes;
import z.note.lite.entity.Post;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Validated
@RestController
public class PostMgmtController {

    @Resource
    private PostMgmtService postMgmtService;

    @GetMapping(Endpoint.Api.POSTS) // /api/posts
    public PageableRes getPosts(
            @Min (1) @RequestParam(defaultValue = "1") int page,
            @Min(1) @Max (10) @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startTs,
            @RequestParam(required = false) String endTs
    ) {
        OffsetDateTime start = null;
        if (StringUtils.hasText(startTs)) {
            start = OffsetDateTime.of(LocalDateTime.parse(startTs, Formatter.DATE_TIME), OffsetDateTime.now().getOffset());
        }
        OffsetDateTime end = null;
        if (StringUtils.hasText(endTs)) {
            end = OffsetDateTime.of(LocalDateTime.parse(endTs, Formatter.DATE_TIME), OffsetDateTime.now().getOffset());
        }
        return postMgmtService.findAll(page, size, id, status, start, end);
    }

    @GetMapping(Endpoint.Api.POST_ID) // /api/post/{postId}
    public Post getPost(@PathVariable Integer postId) {
        return postMgmtService.findById(postId);
    }

    @PostMapping(Endpoint.Api.POST) // /api/post
    public int create(@Validated @RequestBody PostReq post) {
        return postMgmtService.create(post);
    }

    @PutMapping(Endpoint.Api.POST) // /api/post
    public int update(@Validated @RequestBody PostReq post) {
        return postMgmtService.update(post);
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
