package z.note.lite.web.advice.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;
import z.note.lite.web.filter.trace.TraceFilter;

@Setter
@Getter
@Builder
public class Response {

    private String requestId;

    private int code;

    private String msg;

    private Object data;

    public static Response success(Object data) {
        return Response.builder().requestId(MDC.get(TraceFilter.TRACE_ID)).data(data).build();
    }

    public static Response fail(String msg) {
        return Response.builder().requestId(MDC.get(TraceFilter.TRACE_ID)).code(-1).msg(msg).build();
    }

}
