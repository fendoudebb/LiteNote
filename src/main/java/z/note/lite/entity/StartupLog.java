package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
public class StartupLog {

    private Long id;

    private String pid;

    private String port;

    private int cpuCount;

    private long memory;

    private String systemLoad;

    private String userDir;

    private String userRegion;

    private String userLanguage;

    private String commandLine;

    private String appVersion;

    private String runtimeName;

    private String runtimeVersion;

    private String frameworkName;

    private String frameworkVersion;

    private String activeProfiles;

    private String osName;

    private String osArch;

    private String osVersion;

    private String timezone;

    private String startupBy;

    private OffsetDateTime startupTs;

    private long costTime;

    private OffsetDateTime createTs;

}
