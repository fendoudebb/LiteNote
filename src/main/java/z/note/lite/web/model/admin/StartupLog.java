package z.note.lite.web.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "startup_log")
public class StartupLog {

    @Id
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

    private LocalDateTime startupTs;

    private long costTime;

    @ReadOnlyProperty
    private LocalDateTime createTs;

}
