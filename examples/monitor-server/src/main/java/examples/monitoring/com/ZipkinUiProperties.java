package examples.monitoring.com;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by AWA on 16.11.2016.
 */

@ConfigurationProperties("zipkin.ui")
public class ZipkinUiProperties {
    private String environment;
    private int queryLimit = 10;
    private int defaultLookback;
    private String instrumented;

    public ZipkinUiProperties() {
        this.defaultLookback = (int) TimeUnit.DAYS.toMillis(7L);
        this.instrumented = ".*";
    }

    public int getDefaultLookback() {
        return this.defaultLookback;
    }

    public void setDefaultLookback(int defaultLookback) {
        this.defaultLookback = defaultLookback;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getQueryLimit() {
        return this.queryLimit;
    }

    public void setQueryLimit(int queryLimit) {
        this.queryLimit = queryLimit;
    }

    public String getInstrumented() {
        return this.instrumented;
    }

    public void setInstrumented(String instrumented) {
        this.instrumented = instrumented;
    }
}
