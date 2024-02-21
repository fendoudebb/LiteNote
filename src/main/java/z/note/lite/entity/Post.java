package z.note.lite.entity;

import lombok.Data;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.time.OffsetDateTime;
import java.util.List;

@Data
//@Table(name = "post")
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

}
