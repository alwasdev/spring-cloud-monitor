package monitoring.com.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import monitoring.com.client.model.MonitorSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

/**
 * Created by AWA on 06.11.2016.
 */
@Configuration
@Import(SchedulingConfiguration.class)
public class MonitorClientConfiguration extends WebMvcConfigurerAdapter {

    private static Runtime runtime = Runtime.getRuntime();

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private int port;

    @Bean
    public HasFeatures monitorClientFeature() {
        return HasFeatures.namedFeature("Monitor Client", MonitorClientConfiguration.class);
    }

    @Scheduled(fixedDelay = 10000)
    @HystrixCommand(fallbackMethod = "test")
    public void publishUpdates() {
        MonitorSnapshot monitorSnapshot = createMonitorSnapshot();

        restTemplate.postForEntity("http://monitor-server/monitor/push/", monitorSnapshot, String.class);
    }

    private MonitorSnapshot createMonitorSnapshot() {
        MonitorSnapshot monitorSnapshot = new MonitorSnapshot();

        long maxMemory = runtime.maxMemory() / 1024;
        long allocatedMemory = runtime.totalMemory() / 1024;
        long freeMemory = runtime.freeMemory() / 1024;
        long usedMemory = allocatedMemory - freeMemory;
        long timestamp = new Date().getTime();

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        monitorSnapshot.setApplication(appName);
        monitorSnapshot.setActiveThreads(threadSet.size());
        monitorSnapshot.setCommitedMemory(allocatedMemory);
        monitorSnapshot.setFreeMemory(freeMemory);
        monitorSnapshot.setMaxMemory(maxMemory);
        monitorSnapshot.setUsedMemory(usedMemory);
        monitorSnapshot.setTimestamp(timestamp);
        try {
            monitorSnapshot.setHystrixUrl("http://"+InetAddress.getLocalHost().getHostName() + ":" + port + "/hystrix.stream");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return monitorSnapshot;
    }

    public void test() {

    }
}
