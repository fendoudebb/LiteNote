package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@ToString
public class Post {

    private Integer id;

    private Long uid;

    private String title;

    private String description;

    private List<String> topics;

    private String content;

    private String contentHtml;

    private int wordCount;

    private int prop;

    private int status;

    private int pv;

    private int likeCount;

    private int commentCount;

    private int commentStatus;

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

    private List<String> images;

    private Integer prevId;

    private String prevTitle;

    private Integer nextId;

    private String nextTitle;

}
