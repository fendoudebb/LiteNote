package z.note.lite.web.http.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RandomPostRes {

    private Integer id;

    private String title;

    private int pv;

}
