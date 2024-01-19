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
import java.time.Period;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("record_search")
public class Search {

    @Id
    private Integer id;

    private String keywords;

    private long took;

    private String referer;

    private String ua;

    private String uaName;

    private String uaOs;

    private long ipId;

    private int channel; // 0: 网站，1: 微信小程序

    @ReadOnlyProperty
    private LocalDateTime createTs;

    @Transient
    private String ip;

    @Transient
    private String ipAddress;

}
