package telran.pulse.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.dto.SensorJump;
import telran.pulse.monitoring.entities.SensorLastValue;
import telran.pulse.monitoring.repo.SensorRepository;

import java.util.function.Consumer;

@Service
@Slf4j
@ManagedResource
public class AnalyserService {

    StreamBridge streamBridge;
    SensorRepository sensorRepository;

    public AnalyserService(StreamBridge streamBridge, SensorRepository sensorRepository) {
        this.streamBridge = streamBridge;
        this.sensorRepository = sensorRepository;
    }

    @Value("${app.jump.threshold:50}")
    int jumpPercentThreshold;

    @Value("${app.critical.threshold:100}")
    int criticalPercentThreshold;

    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseProcessing;
    }

    private void pulseProcessing(Sensor sensor) {
        log.trace("Received senseor id {}; value {}",
                sensor.id,
                sensor.value);
        SensorLastValue sensorLastValue = sensorRepository.findById(sensor.id).orElse(null);
        if (sensorLastValue == null) {
            log.trace("for sensor id {} not found record in redis", sensor.id);
            sensorLastValue = new SensorLastValue(sensor.id);
        } else {
            int lastValue = sensorLastValue.getLastValue();
            int delta = Math.abs(lastValue - sensor.value);
            double percent = (double) delta / lastValue * 100;
            if (percent > jumpPercentThreshold) {
                log.debug("sensor id {} has values jump {}", sensor.id, delta);
                SensorJump sensorJump = new SensorJump(sensor.id, lastValue, sensor.value);
                streamBridge.send("jumps-out-0", sensorJump);
                if (percent > criticalPercentThreshold) {
                    log.debug("sensor id {} has critical values jump {}", sensor.id, delta);
                    streamBridge.send("critical-jumps-out-0", sensorJump);
                }
            }
        }

        sensorLastValue.addLastValue(sensor.value);
        sensorRepository.save(sensorLastValue);
    }

    @ManagedOperation
    public int getJumpPercentThreshold() {
        return jumpPercentThreshold;
    }

    @ManagedOperation
    public void setJumpPercentThreshold(int jumpPercentThreshold) {
        this.jumpPercentThreshold = jumpPercentThreshold;
    }
}