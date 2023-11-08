package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("topic")
public class Topic {

    @Id
    private Integer id;

    private String name;

    private int sort;

    private LocalDateTime createTs;

    private LocalDateTime updateTs;

}
