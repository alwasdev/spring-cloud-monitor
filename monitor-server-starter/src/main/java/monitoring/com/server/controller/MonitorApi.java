package monitoring.com.server.controller;


import monitoring.com.server.model.MonitorSnapshot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by AWA on 03.11.2016.
 */
@RequestMapping("/monitor")
public interface MonitorApi {

    @RequestMapping(path = "/push", method = RequestMethod.POST)
    ResponseEntity<Map> pushSnapshot(@RequestBody MonitorSnapshot monitorSnapshot);
}
