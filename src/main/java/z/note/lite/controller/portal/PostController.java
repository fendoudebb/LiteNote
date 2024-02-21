package z.note.lite.controller.portal;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import z.note.lite.config.advice.response.Response;
import z.note.lite.controller.Endpoint;
import z.note.lite.entity.Post;
import z.note.lite.service.portal.PostService;

import java.util.Objects;

@Controller
public class PostController {

    @Resource
    private PostService postService;

    @GetMapping(Endpoint.Portal.POST) // /p/{postId}.html
    public String post(@PathVariable Integer postId, Model model, HttpServletResponse response) {
        Post post = postService.getPost(postId);
        if (Objects.isNull(post) || post.getStatus() != 0) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return "error";
        }
        model.addAttribute("post", post);
        return "portal/post";
    }

    @ResponseBody
    @PostMapping(Endpoint.Portal.POST_RANDOM) // /post/random
    public Response randomPost() {
        return Response.builder().data(postService.getRandomPosts()).build();
    }

}
