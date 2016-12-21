package monitoring.com.server.controller;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Import;
import zipkin.server.EnableZipkinServer;

import java.lang.annotation.*;

/**
 * Created by AWA on 06.11.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MonitorServerConfiguration.class)
@EnableHystrix
@EnableHystrixDashboard
@EnableZipkinServer
public @interface EnableMonitorServer {
}
