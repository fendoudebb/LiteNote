package z.note.lite.web.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "startup_log")
public class StartupLog {

    @Id
    private Long id;

    private int pid;

    private int port;

    private int cpuCount;

    private long memory;

    private double systemLoad;

    private String userDir;

    private String command;

    private String appVersion;

    private String jdkVersion;

    private String frameworkVersion;

    private String osName;

    private String osArch;

    private String osVersion;

    private String timeZone;

    private String startupBy;

    private LocalDateTime startupDate;

    private int costTime;

}
