package z.note.lite.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RecordInvalidRequest {

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

    private OffsetDateTime createTs;

    private Long costTime;

    private int ipId;

    private int channel; // 0: 网站，1: 微信小程序

}
