package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Topic {

    @Id
    private Integer id;

    private String name;

    private int sort;

    private LocalDateTime createTs;

    private LocalDateTime updateTs;

}
