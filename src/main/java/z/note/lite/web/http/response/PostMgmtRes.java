package z.note.lite.web.http.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Table("post")
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
    private LocalDateTime createTs;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTs;

}
