package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("ip_pool")
public class Ip {

    @Id
    private Integer id;

    private String ip;

    private String country;

    private String region;

    private String city;

    private String isp;

    private String countryId;

    private String regionId;

    private String cityId;

    private String ispId;

    @ReadOnlyProperty
    private LocalDateTime createTs;

    private LocalDateTime updateTs;

}
