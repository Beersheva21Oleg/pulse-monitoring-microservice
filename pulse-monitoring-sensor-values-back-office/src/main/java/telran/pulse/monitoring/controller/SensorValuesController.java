package telran.pulse.monitoring.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import telran.pulse.monitoring.service.SensorValuesService;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/sensors")
@AllArgsConstructor
public class SensorValuesController {
    SensorValuesService sensorValuesService;

    @GetMapping("/average/{sensorId}")
    int getAvgSensorDates(@PathVariable int sensorId,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return sensorValuesService.getAvgDates(sensorId, from, to);
    }

    @GetMapping("/jumps/{sensorId}")
    long getJumpsCountDates(@PathVariable int sensorId,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return sensorValuesService.getJumpsCountDates(sensorId, from, to);
    }

}
