package monitoring.com;

import monitoring.com.model.MonitorSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Set;

/**
 * Created by AWA on 04.11.2016.
 */
@Configuration
@EnableScheduling
public class MonitorClientConfig {

    private static Runtime runtime = Runtime.getRuntime();

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @Scheduled(fixedDelay = 10000)
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

        return monitorSnapshot;
    }
}


