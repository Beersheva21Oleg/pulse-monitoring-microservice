package telran.pulse.monitoring.service;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.Sensor;

import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class AnalyserService {

    private StreamBridge streamBridge;
    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseProcessing;
    }

    private void pulseProcessing(Sensor sensor) {
        System.out.printf("sequence number %d, sensor id %d, waiting time %d\n",
                sensor.seqNum,
                sensor.id,
                System.currentTimeMillis() - sensor.timestamp);
    }

}