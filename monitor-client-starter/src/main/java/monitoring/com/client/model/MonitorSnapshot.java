package monitoring.com.client.model;

import javax.validation.constraints.NotNull;

/**
 * Created by AWA on 02.11.2016.
 */
public class MonitorSnapshot {

    @NotNull
    private String application;
    private long usedMemory;
    private long freeMemory;
    private long commitedMemory;
    private long maxMemory;

    private int activeThreads;

    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getCommitedMemory() {
        return commitedMemory;
    }

    public void setCommitedMemory(long commitedMemory) {
        this.commitedMemory = commitedMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public int getActiveThreads() {
        return activeThreads;
    }

    public void setActiveThreads(int activeThreads) {
        this.activeThreads = activeThreads;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
