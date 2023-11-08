package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("ip_unknown")
public class IpUnknown {

    @Id
    private String ip;

    @ReadOnlyProperty
    private LocalDateTime createTs;

    private LocalDateTime updateTs;

}
