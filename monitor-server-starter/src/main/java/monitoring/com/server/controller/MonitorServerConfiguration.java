package monitoring.com.server.controller;


import monitoring.com.server.socket.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by AWA on 06.11.2016.
 */
@Configuration
@Import({WebSocketConfig.class, SchedulingConfiguration.class})
public class MonitorServerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Bean
    public HasFeatures monitorServerFeature() {
        return HasFeatures.namedFeature("Monitor Server", MonitorServerConfiguration.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "monitor.server", name = "enabled", matchIfMissing = true)
    public MonitorController monitorController() {
        return new MonitorController(this.simpMessagingTemplate);
    }
}
