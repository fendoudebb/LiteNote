package z.note.lite.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
//@Table("ip_unknown")
public class IpUnknown {

    private String ip;

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

}
