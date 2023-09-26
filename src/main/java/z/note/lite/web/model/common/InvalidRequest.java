package z.note.lite.web.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("record_invalid_request")
public class InvalidRequest {

    @Id
    private Long id;

    private String url;

    private int reqMethod; // 0: GET，1: POST，2: PUT，3: DELETE，4: OPTION

    private String reqParam;

    private String ua;

    private String uaName;

    private String uaCategory;

    private String uaVersion;

    private String uaVendor;

    private String uaOs;

    private String uaOsVersion;

    private String referer;

    @ReadOnlyProperty
    private LocalDateTime createTs;

    private long costTime;

    private int ipId;

    private int source; // 0: 网站，1: 微信小程序

}
