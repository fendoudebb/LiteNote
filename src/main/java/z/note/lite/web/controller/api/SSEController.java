package z.note.lite.web.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;

@RestController
public class SSEController {

    @GetMapping("Endpoint.Api.SSE") // /api/sse
    public SseEmitter sseEmitter(Principal principal) {
        return null;
    }


}
