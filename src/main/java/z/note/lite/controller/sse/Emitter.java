package z.note.lite.controller.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Emitter {

    private static final Map<String, SseEmitter> container = new ConcurrentHashMap<>();

    public static void connect(String username, SseEmitter emitter) {
        emitter.onCompletion(() -> container.remove(username, emitter));
        emitter.onError(callback -> container.remove(username, emitter));
        emitter.onTimeout(() -> container.remove(username, emitter));
        container.put(username, emitter);
    }

    public static void emit(String username, Object data) throws IOException {
        SseEmitter emitter = container.get(username);
        emitter.send(SseEmitter.event().data(data).build());
    }

    public static void emitAll(Object data) {
        container.forEach((username, sseEmitter) -> {
            try {
                sseEmitter.send(SseEmitter.event().data(data).build());
            } catch (Throwable e) {
                log.error("emit error, username: {}, data: {}, exception: {}", username, data, e.getMessage());
            }
        });
    }



}
