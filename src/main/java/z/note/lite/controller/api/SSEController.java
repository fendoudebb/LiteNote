package z.note.lite.controller.api;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import z.note.lite.controller.sse.Emitter;
import z.note.lite.controller.Endpoint;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
public class SSEController {

    @GetMapping(Endpoint.Api.SSE) // /api/sse
    public SseEmitter sseEmitter(Principal principal) {
        SseEmitter sseEmitter = new SseEmitter(-1L);
        Emitter.connect(principal.getName(), sseEmitter);
        return sseEmitter;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void countOnlinePost() {
        Emitter.emitAll(LocalDateTime.now());
    }

}
