package z.note.lite.config.advice.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Request {

    private String id;

    private Object payload;

}
