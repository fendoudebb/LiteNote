package z.note.lite.web.controller.mobile;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.service.mobile.MobilePostService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.http.response.Res;

@RestController
public class MobileController {

    @Resource
    private MobilePostService mobilePostService;

    @GetMapping(Endpoint.Mobile.INDEX) // /m/index
    public Res index(@RequestParam(defaultValue = "1") int page,
                     @RequestParam(defaultValue = "20") int size,
                     @RequestParam(required = false) String topic) {
        return new Res(mobilePostService.getOnlinePosts(topic, page, size));
    }

    @GetMapping(Endpoint.Mobile.POST) // /m/p/{postId}
    public Res post(@PathVariable Integer postId) {
        return new Res(mobilePostService.getPost(postId));
    }

    @GetMapping(Endpoint.Mobile.SEARCH) // /m/search/{keywords}
    public Res search(@PathVariable String keywords, @RequestParam(defaultValue = "1") int page) {
        return new Res(mobilePostService.search(keywords, page));
    }

}
