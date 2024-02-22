package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
public class IpUnknown {

    private String ip;

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

}
