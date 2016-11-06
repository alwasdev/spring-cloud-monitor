package monitoring.com.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by AWA on 06.11.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MonitorClientConfiguration.class)
public @interface EnableMonitorClient {
}
