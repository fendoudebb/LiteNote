package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "post")
public class Post {

    @Id
    private Integer id;

    private int uid;

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

    private LocalDateTime createTs;

    private LocalDateTime updateTs;

}
