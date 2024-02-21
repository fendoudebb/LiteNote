package z.note.lite.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
//@Table("topic")
public class Topic {

    private Integer id;

    private String name;

    private int sort;

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

}
