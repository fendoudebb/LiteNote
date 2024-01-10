package z.note.lite.web.http.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Res {

    private int code;

    private Object data;

    private String msg;

    public Res(Object data) {
        this.data = data;
    }

}
