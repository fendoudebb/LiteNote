package z.note.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordSearch {

    private Integer id;

    private String keywords;

    private long took;

    private String referer;

    private String ua;

    private String uaName;

    private String uaOs;

    private long ipId;

    private int channel; // 0: 网站，1: 微信小程序

    private OffsetDateTime createTs;

    private String ip;

    private String ipAddress;

}
