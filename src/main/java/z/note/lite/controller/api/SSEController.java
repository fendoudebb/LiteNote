package z.note.lite.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;

@RequestMapping("/api")
@RestController
public class SSEController {

    @GetMapping("/sse")
    public SseEmitter sseEmitter(Principal principal) {
        return null;
    }


}
