package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
public class Topic {

    private Integer id;

    private String name;

    private int sort;

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

}
