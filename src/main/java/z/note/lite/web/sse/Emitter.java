package z.note.lite.web.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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



}
