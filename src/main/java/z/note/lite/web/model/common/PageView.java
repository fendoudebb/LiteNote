package z.note.lite.web.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("record_page_view")
public class PageView {

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

    private Long costTime;

    private int ipId;

    private int source; // 0: 网站，1: 微信小程序

    private Integer postId;

    @Transient
    private String ip;

    @Transient
    private String ipAddress;

}
