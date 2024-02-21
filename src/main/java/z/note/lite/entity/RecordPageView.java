package z.note.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table("record_page_view")
public class RecordPageView {

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

    private Integer postId;

    private String ip;

    private String ipAddress;

}
