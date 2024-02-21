package z.note.lite.config.context;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Data
@Builder
public class Visitor {

    private int statusCode;

    private String ip;

    private String lang;

    private boolean crawler;

    private Locale locale;

    private String reqUrl;

    private short reqMethod; // 0: GET，1: POST，2: PUT，3: DELETE，4: OPTION

    private String reqParam;

    private String ua;

    private String uaName;

    private String uaCategory;

    private String uaVersion;

    private String uaVendor;

    private String uaOs;

    private String uaOsVersion;

    private String referer;

    private long costTime;

    private long ipId;

    private short source; // 0: 网站，1: 微信小程序

    private int postId;

}
