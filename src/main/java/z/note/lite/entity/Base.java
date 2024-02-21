package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Setter
@Getter
public abstract class Base {

    private Long id;

    private String createBy;

    private OffsetDateTime createTs;

    private String updateBy;

    private OffsetDateTime updateTs;

}
