package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
public class Ip {

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

    private OffsetDateTime createTs;

    private OffsetDateTime updateTs;

}
