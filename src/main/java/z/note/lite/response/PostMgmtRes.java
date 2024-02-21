package z.note.lite.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@ToString
//@Table("post")
public class PostMgmtRes {

    private Integer id;

    private String title;

    private List<String> topics;

    private int wordCount;

    private int prop;

    private int status;

    private int pv;

    private int commentStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createTs;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updateTs;

}
