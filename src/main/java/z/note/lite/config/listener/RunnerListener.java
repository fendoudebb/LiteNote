package z.note.lite.config.listener;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import z.note.lite.entity.StartupLog;
import z.note.lite.mapper.api.StartupLogMapper;
import z.note.lite.service.api.StartupLogService;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class RunnerListener implements ApplicationRunner {

    @Resource
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Resource
    WebApplicationContext applicationContext;

    @Value("${preferences.version}")
    String appVersion;

    @Resource
    StartupLogService startupLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        RequestMappingInfoHandlerMapping mapping = applicationContext.getBean(RequestMappingInfoHandlerMapping.class);

        Map<String, RequestMappingInfoHandlerMapping> map = applicationContext.getBeansOfType(
                RequestMappingInfoHandlerMapping.class);

        map.forEach((s, requestMappingInfoHandlerMapping) -> {
            System.out.println("-------------- " + s + " --------------");
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingInfoHandlerMapping.getHandlerMethods();
            handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
                System.out.println(requestMappingInfo.getDirectPaths() + ", name#" + requestMappingInfo.getName() + ", method#" + requestMappingInfo.getMethodsCondition().getMethods());
            });
        });


        // 拿到Handler适配器中的全部方法
        Map<RequestMappingInfo, HandlerMethod> methodMap = requestMappingHandlerMapping.getHandlerMethods();
//        Map<String, String> urlMap = new HashMap<>();
        for (RequestMappingInfo info : methodMap.keySet()){
            //获取请求路径
            Set<String> directPaths = info.getDirectPaths();
            // 获取全部请求方式
            //Set<RequestMethod> Methods = info.getMethodsCondition().getMethods();
            //获取全部请求名称
            String urlName =  info.getName();
            for (String url : directPaths){
                System.out.println("urlname#" + urlName + ", url#" + url + ", " + info.getMethodsCondition().getMethods());
            }
        }

        Environment environment = applicationContext.getEnvironment();
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
        StartupLog startupLog = new StartupLog();
        startupLog.setPid(System.getProperty("PID"));
        startupLog.setPort(Objects.requireNonNull(environment.getProperty("server.port")));
        startupLog.setCpuCount(Runtime.getRuntime().availableProcessors());
        startupLog.setMemory(Runtime.getRuntime().maxMemory());
        startupLog.setSystemLoad(String.valueOf(operatingSystem.getSystemLoadAverage()));
        startupLog.setUserDir(System.getProperty("user.dir"));
        startupLog.setUserRegion(System.getProperty("user.country"));
        startupLog.setUserLanguage(System.getProperty("user.language"));
        startupLog.setCommandLine(String.join(" ", runtime.getInputArguments()));
        startupLog.setAppVersion(appVersion);
        startupLog.setRuntimeName(System.getProperty("java.vm.name"));
        startupLog.setRuntimeVersion(System.getProperty("java.vendor.version"));
        startupLog.setFrameworkName("Spring Boot");
        startupLog.setFrameworkVersion(SpringBootVersion.getVersion());
        startupLog.setActiveProfiles(String.join(" ", environment.getActiveProfiles()));
        startupLog.setOsName(System.getProperty("os.name"));
        startupLog.setOsArch(System.getProperty("os.arch"));
        startupLog.setOsVersion(System.getProperty("os.version"));
        startupLog.setTimezone(System.getProperty("user.timezone"));
        startupLog.setStartupBy(System.getProperty("user.name"));
        startupLog.setStartupTs(Instant.ofEpochMilli(runtime.getStartTime()).atZone(ZoneId.systemDefault()).toOffsetDateTime());
        startupLog.setCostTime(System.currentTimeMillis() - runtime.getStartTime());
        startupLogService.create(startupLog);
    }

}
