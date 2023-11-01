package z.note.lite.web.http.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageableRes {

    private long count;

    private Object list;

}
