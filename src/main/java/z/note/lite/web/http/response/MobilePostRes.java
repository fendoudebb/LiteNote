package z.note.lite.web.http.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class MobilePostRes {

    private Integer postId;

    private String title;

    private String description;

    private List<String> topics;

    private Long postTime;

    private Integer pv;

    private Integer postWordCount;

    private String contentHtml;

}
