package monitoring.com.server.controller;

import monitoring.com.server.model.MonitorSnapshot;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AWA on 03.11.2016.
 */
@Controller
@EnableScheduling
public class MonitorController implements MonitorApi {

    Map<String, MonitorSnapshot> monitorSnapshotMap = new HashMap<>();

    private SimpMessagingTemplate simpMessagingTemplate;

    public MonitorController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public ResponseEntity<Map> pushSnapshot(@Validated @RequestBody MonitorSnapshot monitorSnapshot) {
        monitorSnapshotMap.put(monitorSnapshot.getApplication(), monitorSnapshot);
        return new ResponseEntity<>(monitorSnapshotMap, HttpStatus.OK);
    }

    @Scheduled(fixedDelay = 10000)
    public void publishUpdates() {
        simpMessagingTemplate.convertAndSend("/topic/monitor", monitorSnapshotMap);
    }
}
