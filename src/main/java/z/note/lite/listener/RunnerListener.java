package z.note.lite.listener;

import com.sun.jdi.VirtualMachineManager;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import jdk.jfr.FlightRecorder;
import jdk.management.jfr.FlightRecorderMXBean;
import org.apache.tomcat.util.Diagnostics;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.SpringVersion;
import org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.Clock;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class RunnerListener implements ApplicationRunner, ApplicationContextAware {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    WebApplicationContext applicationContext;

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
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        long startupDate = applicationContext.getStartupDate();
        System.out.println(startupDate);
        System.out.println(applicationContext.getId());
        var environment = applicationContext.getEnvironment();
        System.out.println(Arrays.stream(environment.getActiveProfiles()).toList());
        System.out.println(ProcessHandle.current().pid());
        ProcessHandle.Info info = ProcessHandle.current().info();
        System.out.println(info);
        System.out.println(System.getProperty("PID"));
        System.out.println(environment.getProperty("server.port"));
        System.out.println(System.getProperty("java.version"));
        System.out.println(Runtime.version());
        System.out.println(Runtime.version().toString());
        System.out.println(JavaVersion.getJavaVersion().toString());
        System.out.println(SpringBootVersion.getVersion());
        System.out.println(SpringVersion.getVersion());

        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("user.dir"));
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("runtimeMXBean#" + runtimeMXBean.getPid());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getStartTime());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getClassPath());
//        System.out.println("runtimeMXBean#" + runtimeMXBean.getBootClassPath());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getLibraryPath());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getInputArguments());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getManagementSpecVersion());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getVmName());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getVmVendor());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getVmVersion());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getSpecName());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getSpecVersion());
        System.out.println("runtimeMXBean#" + runtimeMXBean.getSpecVendor());

        System.out.println("------------------- OperatingSystemMXBean -------------------");

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(operatingSystemMXBean.getArch());
        System.out.println(operatingSystemMXBean.getSystemLoadAverage());

//        System.out.println(TimeZone.getDefault());


        System.out.println(ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount());
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            System.out.println(garbageCollectorMXBean.getName() + " " + garbageCollectorMXBean.getCollectionCount() + " " + garbageCollectorMXBean.getObjectName());
        }

        FlightRecorderMXBean flightRecorderMXBean = ManagementFactory.getPlatformMXBean(FlightRecorderMXBean.class);
        long l = flightRecorderMXBean.newRecording();
        flightRecorderMXBean.startRecording(l);

        flightRecorderMXBean.stopRecording(l);
        try {
            flightRecorderMXBean.closeRecording(l);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
//        hotSpotDiagnosticMXBean.dumpHeap();

        for (VMOption vmOption : hotSpotDiagnosticMXBean.getDiagnosticOptions()) {
            System.out.println("vmOption#" + vmOption.getName());
        }

        System.out.println("------------------- PlatformLoggingMXBean -------------------");
        PlatformLoggingMXBean platformLoggingMXBean = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);
        System.out.println(platformLoggingMXBean.getLoggerNames());

        System.out.println("------------------- MBean -------------------");

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            Set<ObjectInstance> objectInstances = mBeanServer.queryMBeans(ObjectName.WILDCARD, null);
            for (ObjectInstance objectInstance : objectInstances) {
                System.out.println(objectInstance);
            }

            System.out.println("----------");

            Set<ObjectName> objectNames = mBeanServer.queryNames(ObjectName.WILDCARD, null);
            for (ObjectName objectName : objectNames) {
                System.out.println(objectName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}
